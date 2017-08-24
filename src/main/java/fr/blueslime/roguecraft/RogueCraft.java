package fr.blueslime.roguecraft;

import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.ArenasManager;
import fr.blueslime.roguecraft.commands.CommandRogueCraft;
import fr.blueslime.roguecraft.events.*;
import fr.blueslime.roguecraft.stuff.StuffManager;
import java.io.File;
import net.samagames.gameapi.GameAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * This file is part of RogueCraft.
 *
 * RogueCraft is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RogueCraft is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with RogueCraft.  If not, see <http://www.gnu.org/licenses/>.
 */
public class RogueCraft extends JavaPlugin
{
    private static RogueCraft plugin;
    private Arena arena;
    private StuffManager stuffManager;
    private String bungeeName;
    private int comPort;

    @Override
    public void onEnable()
    {
        super.onEnable();
        plugin = this;
        
        this.saveDefaultConfig();
        this.comPort = getConfig().getInt("com-port");
        this.bungeeName = getConfig().getString("BungeeName");
        
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        
        World world = Bukkit.getWorlds().get(0);
        File file = new File(world.getWorldFolder(), "arena.json");
        
        if (!file.exists())
        {
            Bukkit.getLogger().severe("#==================[Fatal exception report]==================#");
            Bukkit.getLogger().severe("# The arena.yml description file was NOT FOUND.              #");
            Bukkit.getLogger().severe("# The plugin cannot load without it, please create it.       #");
            Bukkit.getLogger().severe("# The file path is the following :                           #");
            Bukkit.getLogger().severe(file.getAbsolutePath());
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        
        this.stuffManager = new StuffManager();
        this.arena = new ArenasManager().loadArena(file);
                        
        this.getCommand("rc").setExecutor(new CommandRogueCraft());
        this.registerEvents();
        
        GameAPI.registerGame("roguecraft", comPort, bungeeName);
        GameAPI.getManager().sendArenas();
    }

    @Override
    public void onDisable() {}
    
    public void registerEvents()
    {
        Bukkit.getPluginManager().registerEvents(new RCBlockBreakEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCBlockPhysicsEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCBlockPlaceEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCEntityDamageByEntityEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCEntityExplodeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCInventoryOpenEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCPlayerDeathEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCPlayerDropItemEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCPlayerInteractEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCPlayerQuitEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCPlayerRespawnEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCEntityDamageEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCEntityDeathEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCFinishJoinPlayerEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCPreJoinPlayerEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCPlayerPickupItemEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCEntityRegainHealthEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCFoodLevelChangeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCJoinModEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCEntityTargetEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCEntityCombustEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCEntityChangeBlockEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCProjectileHitEvent(), this);
    }

    public Arena getArena()
    {
        return this.arena;
    }

    public StuffManager getStuffManager()
    {
        return this.stuffManager;
    }
    
    public int getComPort()
    {
        return this.comPort;
    }
    
    public String getBungeeName()
    {
        return this.bungeeName;
    }

    public static RogueCraft getPlugin()
    {
        return plugin;
    }
}
