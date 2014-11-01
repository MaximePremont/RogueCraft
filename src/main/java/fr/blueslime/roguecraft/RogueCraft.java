package fr.blueslime.roguecraft;

import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.ArenasManager;
import fr.blueslime.roguecraft.commands.CommandRogueCraft;
import fr.blueslime.roguecraft.events.*;
import fr.blueslime.roguecraft.stuff.StuffManager;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityTypes;
import net.samagames.gameapi.GameAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

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
        this.registerEntity("WitherBoss", 64, CustomEntityWither.class);
        
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
    }
    
    public void registerEntity(String name, int id, Class<? extends Entity> customClass)
    {
        try
        {
            List<Map<?, ?>> dataMaps = new ArrayList<>();
            
            for (Field f : EntityTypes.class.getDeclaredFields())
            {
                if (f.getType().getSimpleName().equals(Map.class.getSimpleName()))
                {
                    f.setAccessible(true);
                    dataMaps.add((Map<?, ?>) f.get(null));
                }
            }

            ((Map<Class<? extends Entity>, String>) dataMaps.get(1)).put(customClass, name);
            ((Map<Class<? extends Entity>, Integer>) dataMaps.get(3)).put(customClass, id);

        }
        catch (IllegalAccessException | IllegalArgumentException | SecurityException e) 
        {
            e.printStackTrace();
        }
    }

    public void kickPlayer(final Player player)
    {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        try
        {
            out.writeUTF("Connect");
            out.writeUTF("lobby");
        }
        catch (IOException ex) {}
        
        player.sendPluginMessage(this, "BungeeCord", b.toByteArray());
        
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
        {
            @Override
            public void run()
            {
                player.kickPlayer("Une erreur s'est produite lors de la tentative de kick.");
            }
        }, 3*20L);
    }

    public ItemStack getLeaveItem()
    {
        ItemStack leave = new ItemStack(Material.WOOD_DOOR, 1);
        ItemMeta leavemeta = leave.getItemMeta();
        leavemeta.setDisplayName(ChatColor.GOLD + "Quitter le jeu");
        leave.setItemMeta(leavemeta);
        
        return leave;
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
