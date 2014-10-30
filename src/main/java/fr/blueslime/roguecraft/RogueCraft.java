package fr.blueslime.roguecraft;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import fr.blueslime.roguecraft.arena.ArenasManager;
import fr.blueslime.roguecraft.commands.CommandRogueCraft;
import fr.blueslime.roguecraft.events.*;
import fr.blueslime.roguecraft.stuff.StuffManager;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.samagames.network.Network;
import net.samagames.network.client.GamePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RogueCraft extends GamePlugin
{
    private static RogueCraft plugin;
    private StuffManager stuffManager;
    private String bungeeName;
    private int comPort;
    
    public RogueCraft()
    {
        super("roguecraft");
    }
    
    @Override
    public void onEnable()
    {
        super.onEnable();
        plugin = this;
        
        this.saveDefaultConfig();
        this.comPort = getConfig().getInt("com-port");
        this.bungeeName = getConfig().getString("BungeeName");
        
        this.arenaManager = new ArenasManager();
        
        try
        {
            ((ArenasManager) this.arenaManager).loadArenas();
        }
        catch (IOException ex)
        {
            Logger.getLogger(RogueCraft.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        this.stuffManager = new StuffManager();
        
        this.getCommand("rc").setExecutor(new CommandRogueCraft());
        
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        this.registerEvents();
        
        Network.registerGame(this, this.comPort, this.bungeeName);
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
        Bukkit.getPluginManager().registerEvents(new RCEntityRegainHealthEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCFoodLevelChangeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCInventoryOpenEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCPlayerChatEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCPlayerDeathEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCPlayerDropItemEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCPlayerInteractEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCPlayerQuitEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RCPlayerRespawnEvent(), this);
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
    
    public void kickPlayer(final Player player, String reason)
    {
        player.sendMessage(ChatColor.RED + reason);
        this.kickPlayer(player);
    }

    public ItemStack getLeaveItem()
    {
        ItemStack leave = new ItemStack(Material.WOOD_DOOR, 1);
        ItemMeta leavemeta = leave.getItemMeta();
        leavemeta.setDisplayName(ChatColor.GOLD + "Quitter le jeu");
        leave.setItemMeta(leavemeta);
        
        return leave;
    }
    
    public ArenasManager getArenasManager()
    {
        return (ArenasManager) this.arenaManager;
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
