package fr.blueslime.roguecraft.arena;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.blueslime.roguecraft.arena.Wave.WaveType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import net.samagames.network.Network;
import net.samagames.network.client.GameArena;
import net.samagames.network.client.GameArenaManager;
import net.samagames.network.json.Status;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class ArenasManager extends GameArenaManager
{
    public void loadArenas() throws FileNotFoundException, IOException
    {
        Bukkit.getLogger().info("Loading arenas...");
        
        for (World w : Bukkit.getWorlds())
        {
            File folder = new File(w.getWorldFolder(), "arenas");
            
            if (!folder.exists())
            {
                Bukkit.getLogger().warning("Arenas load failed for world " + w.getName() + " : folder " + folder.getAbsolutePath() + " not found !");
                continue;
            }

            for (File arena : folder.listFiles())
            {
                Bukkit.getLogger().info("[ArenaLoad][" + w.getName() + "] Found arena " + arena.getName() + ", attempting to load.");
                BufferedReader br = new BufferedReader(new FileReader(arena));
                StringBuilder builder = new StringBuilder();
                String currentLine;
                
                while((currentLine = br.readLine()) != null)
                {
                    builder.append(currentLine);
                }
                
                JsonElement json = new JsonParser().parse(builder.toString());
                JsonObject jsonObject = json.getAsJsonObject();

                int maxPlayers = jsonObject.get("max-players").getAsInt();
                String mapName = jsonObject.get("mapname").getAsString();
                UUID arenaID;
                
                if(jsonObject.has("uuid")) { arenaID = UUID.fromString(jsonObject.get("uuid").getAsString()); }
                else { arenaID = UUID.randomUUID(); }
                
                Arena arenaa = new Arena(w, maxPlayers, 0, mapName, arenaID);
                arenaa.setDataSource(arena);
                arenaa.setTheme(jsonObject.get("theme").getAsString());
                arenaa.setMinPlayers(jsonObject.get("min-players").getAsInt());
                
                JsonArray jsonAreas = jsonObject.getAsJsonArray("areas");
                
                for(int i = 0; i < jsonAreas.size(); i++)
                {
                    JsonObject area = jsonAreas.get(i).getAsJsonObject();
                    WaveType waveType = WaveType.valueOf(area.get("wave-type").getAsString());
                    
                    JsonObject jsonPlayersSpawn = area.getAsJsonObject("players-spawn");
                    Location playersSpawn = new Location(arenaa.getWorld(), jsonPlayersSpawn.get("x").getAsDouble(), jsonPlayersSpawn.get("y").getAsDouble(), jsonPlayersSpawn.get("z").getAsDouble());
                    
                    JsonArray jsonMobSpawns = area.getAsJsonArray("mob-spawns");
                    ArrayList<Location> mobSpawns = new ArrayList<>();
                    
                    for(int j = 0; j < jsonMobSpawns.size(); j++)
                    {
                        JsonObject mobSpawn = jsonMobSpawns.get(j).getAsJsonObject();
                        mobSpawns.add(new Location(arenaa.getWorld(), mobSpawn.get("x").getAsDouble(), mobSpawn.get("y").getAsDouble(), mobSpawn.get("z").getAsDouble()));
                    }
                    
                    JsonArray jsonBonusChestSpawns = area.getAsJsonArray("bonus-chest-spawns");
                    ArrayList<Location> bonusChestSpawns = new ArrayList<>();
                    
                    for(int j = 0; j < jsonBonusChestSpawns.size(); j++)
                    {
                        JsonObject bonusChest = jsonBonusChestSpawns.get(j).getAsJsonObject();
                        bonusChestSpawns.add(new Location(arenaa.getWorld(), bonusChest.get("x").getAsDouble(), bonusChest.get("y").getAsDouble(), bonusChest.get("z").getAsDouble()));
                    }
                    
                    arenaa.registerArea(waveType, new Area(playersSpawn, mobSpawns, bonusChestSpawns));
                }

                arenas.put(arenaa.getArenaID(), arenaa);
                Bukkit.getLogger().info("[ArenaLoad][" + w.getName() + "] Successfully loaded arena " + arenaa.getArenaID() + " !");
            }
            
            Bukkit.getLogger().info("[ArenaLoad] Loaded world " + w.getName());
        }
        
        Bukkit.getLogger().info("[ArenaLoad] Task ended. " + arenas.size() + " arenas loaded.");
    }

    @Override
    public void disable()
    {
        for(GameArena arena : this.arenas.values())
        {
            arena.setStatus(Status.Stopping);
        }
        
        Network.getManager().sendArenas();
    }
}
