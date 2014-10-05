package fr.blueslime.roguecraft.arena;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import net.samagames.network.Network;
import net.samagames.network.client.GameArena;
import net.samagames.network.client.GameArenaManager;
import net.samagames.network.json.Status;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

public class ArenasManager extends GameArenaManager
{
    public void loadArenas()
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
                YamlConfiguration arenaData = YamlConfiguration.loadConfiguration(arena);
                
                if (arenaData == null)
                {
                    Bukkit.getLogger().warning("[ArenaLoad][" + w.getName() + "] Failed to load " + arena.getName() + " !");
                    continue;
                }
                
                int maxPlayers = arenaData.getInt("max-players");
                String mapName = arenaData.getString("mapname");
                UUID arenaID = UUID.fromString(arenaData.getString("uuid", UUID.randomUUID().toString()));
                
                Arena arenaa = new Arena(w, maxPlayers, 0, mapName, arenaID);
                arenaa.setDataSource(arena);
                arenaData.set("uuid", arenaa.getArenaID().toString());
                
                try
                {
                    arenaData.save(arena);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                arenaa.setTheme(arenaData.getString("theme"));
                arenaa.setMinPlayers(arenaData.getInt("min-players"));

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
