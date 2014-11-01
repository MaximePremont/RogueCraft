package fr.blueslime.roguecraft.arena;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Wave.WaveType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import net.samagames.gameapi.GameAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ArenasManager
{    
    public Arena loadArena(File file)
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder builder = new StringBuilder();
            String currentLine;
            
            while((currentLine = br.readLine()) != null)
            {
                builder.append(currentLine);
            }
            
            JsonElement json = new JsonParser().parse(builder.toString());
            JsonObject jsonObject = json.getAsJsonObject();
            
            UUID arenaID;
            
            if(jsonObject.has("uuid")) { arenaID = UUID.fromString(jsonObject.get("uuid").getAsString()); }
            else { arenaID = UUID.randomUUID(); }
            
            Arena arena = new Arena(arenaID, Bukkit.getWorlds().get(0));
            arena.setMapName(jsonObject.get("mapname").getAsString());
            
            JsonArray jsonAreas = jsonObject.getAsJsonArray("areas");
            
            for(int i = 0; i < jsonAreas.size(); i++)
            {
                JsonObject area = jsonAreas.get(i).getAsJsonObject();
                WaveType waveType = WaveType.valueOf(area.get("wave-type").getAsString());
                
                JsonObject jsonPlayersSpawn = area.getAsJsonObject("players-spawn");
                Location playersSpawn = new Location(arena.getWorld(), jsonPlayersSpawn.get("x").getAsDouble(), jsonPlayersSpawn.get("y").getAsDouble(), jsonPlayersSpawn.get("z").getAsDouble());
                
                JsonArray jsonMobSpawns = area.getAsJsonArray("mob-spawns");
                ArrayList<Location> mobSpawns = new ArrayList<>();
                
                for(int j = 0; j < jsonMobSpawns.size(); j++)
                {
                    JsonObject mobSpawn = jsonMobSpawns.get(j).getAsJsonObject();
                    mobSpawns.add(new Location(arena.getWorld(), mobSpawn.get("x").getAsDouble(), mobSpawn.get("y").getAsDouble(), mobSpawn.get("z").getAsDouble()));
                }
                
                JsonArray jsonBonusChestSpawns = area.getAsJsonArray("bonus-chest-spawns");
                ArrayList<Location> bonusChestSpawns = new ArrayList<>();
                
                for(int j = 0; j < jsonBonusChestSpawns.size(); j++)
                {
                    JsonObject bonusChest = jsonBonusChestSpawns.get(j).getAsJsonObject();
                    bonusChestSpawns.add(new Location(arena.getWorld(), bonusChest.get("x").getAsDouble(), bonusChest.get("y").getAsDouble(), bonusChest.get("z").getAsDouble()));
                }
                
                arena.registerArea(waveType, new Area(playersSpawn, mobSpawns, bonusChestSpawns));
            }
            
            GameAPI.registerArena(arena);
            Bukkit.getLogger().info("[ArenaLoad] Successfully loaded arena " + arena.getUUID() + " !");
            
            return arena;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        
        return null;
    }
}
