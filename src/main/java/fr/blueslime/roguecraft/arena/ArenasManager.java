package fr.blueslime.roguecraft.arena;

import fr.blueslime.roguecraft.RogueCraft;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class ArenasManager
{
    private RogueCraft plugin;
    private HashMap<String, Arena> arenas = new HashMap<>();
    private HashMap<UUID, String> joinWait = new HashMap<>();

    public ArenasManager(RogueCraft plugin)
    {
        this.plugin = plugin;
    }

    public HashMap<String, Arena> getArenas()
    {
        return arenas;
    }

    public HashMap<UUID, String> attempts()
    {
        return joinWait;
    }

    public boolean isAttempted(VirtualPlayer player)
    {
        return joinWait.containsKey(player.getPlayerID());
    }

    public String prepareJoin(UUID plid, String arena)
    {
        final VirtualPlayer player = new VirtualPlayer(plid);
        System.out.println("Trying to add player " + plid + " in arena " + arena);
        
        if (getPlayerArena(player) != null)
        {
            return ChatColor.RED + "Vous êtes déjà dans une arène.";
        }
        
        if (joinWait.containsKey(player.getPlayerID()))
        {
            System.out.println("Returned good : player already in list.");
            return "good";
        }
        
        Arena arenaa = arenas.get(arena);
        
        if (arenaa == null)
        {
            return ChatColor.RED + "Une erreur s'est produite : l'arène demandée n'existe pas";
        }
        
        if (!arenaa.canJoin())
        {
            return ChatColor.RED + "Il est impossible de rejoindre l'arène pour le moment.";
        }
        
        System.out.println("Added player !");
        joinWait.put(player.getPlayerID(), arena);
        
        Bukkit.getScheduler().runTaskLaterAsynchronously(RogueCraft.getPlugin(),
            new Runnable()
            {
                public void run()
                {
                    String val = joinWait.remove(player.getPlayerID());
                    System.out.println("Removed " + val);
                }
            }, 5 * 20L
        );
        
        return "good";
    }

    public Arena getArena(String name)
    {
        return arenas.get(name);
    }
    
    public Arena getArena(UUID uuid)
    {
        Iterator<String> keySet = this.arenas.keySet().iterator();
        
        while(keySet.hasNext())
        {
            if(this.arenas.get(keySet.next()).getArenaId().equals(uuid))
                return this.arenas.get(keySet.next());
        }
        
        return null;
    }

    public Arena getPlayerArena(VirtualPlayer player)
    {
        for (Arena ar : arenas.values())
        {
            if (ar.isPlaying(player))
            {
                return ar;
            }
        }
        return null;
    }

    public Arena getPlayerArena(UUID player)
    {
        return getPlayerArena(new VirtualPlayer(player));
    }

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

                Arena arenaa = new Arena(w);
                arenaa.setDataSource(arena);
                arenaa.setArenaId(UUID.fromString(arenaData.getString("uuid", UUID.randomUUID().toString())));
                arenaData.set("uuid", arenaa.getArenaId().toString());
                
                try
                {
                    arenaData.save(arena);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                arenaa.setArenaName(arenaData.getString("name"));
                arenaa.setMapName(arenaData.getString("mapname"));
                arenaa.setTheme(arenaData.getString("theme"));
                arenaa.setMaxPlayers((Integer) arenaData.getInt("max-players"));
                arenaa.setMinPlayers(arenaData.getInt("min-players"));

                arenas.put(arenaa.getArenaName(), arenaa);
                Bukkit.getLogger().info("[ArenaLoad][" + w.getName() + "] Successfully loaded arena " + arenaa.getArenaName() + " !");
            }
            
            Bukkit.getLogger().info("[ArenaLoad] Loaded world " + w.getName());
        }
        
        Bukkit.getLogger().info("[ArenaLoad] Task ended. " + arenas.size() + " arenas loaded.");
    }

    public String finishJoin(Player p)
    {
        VirtualPlayer player = new VirtualPlayer(p);
        
        if (!isAttempted(player))
        {
            return "Vous n'êtes pas en attente.";
        }
        
        Arena arena = getArena(joinWait.get(player.getPlayerID()));
        joinWait.remove(player.getPlayerID());
        
        if (arena == null)
        {
            return "L'arène est introuvable";
        }
        if (!arena.canJoin())
        {
            return "Arène non joignable.";
        }

        return arena.addPlayer(player.getPlayer());
    }
}
