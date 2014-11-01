package fr.blueslime.roguecraft.arena;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena.Role;
import fr.blueslime.roguecraft.stuff.PlayerStuff;
import fr.blueslime.roguecraft.stuff.PlayerStuffDeserializer;
import fr.blueslime.roguecraft.stuff.StuffManager.PlayerClass;
import java.util.UUID;
import net.zyuiop.MasterBundle.FastJedis;
import net.zyuiop.coinsManager.CoinsManager;
import net.zyuiop.statsapi.StatsApi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import redis.clients.jedis.ShardedJedis;

public class ArenaPlayer
{
    private final Arena arena;
    private final Player player;
    private final UUID playerId;
    private Role role;
    private final Scoreboard board;
    private final Objective bar;
    
    private final PlayerStuff pStuff;
    private ItemStack[] armor;
    private ItemStack weapon;
    private PlayerClass pClass;
    
    private int coins;
    private int xp;
    
    public ArenaPlayer(Arena arena, Player player)
    {
        this.arena = arena;
        this.player = player;
        this.playerId = player.getUniqueId();
        this.role = Role.PLAYER;
        
        ShardedJedis redis = FastJedis.jedis();
        
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(PlayerStuff.class, new PlayerStuffDeserializer());
        
        Gson gson = gsonBuilder.create();
        this.pStuff = gson.fromJson(redis.get("roguecraft:properties:" + player.getUniqueId()), PlayerStuff.class);
        
        redis.disconnect();
        
        this.board = Bukkit.getScoreboardManager().getNewScoreboard();
        
        this.bar = this.board.registerNewObjective("Infos", "dummy");
        this.bar.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.bar.setDisplayName("" + ChatColor.DARK_AQUA + ChatColor.BOLD + "RogueCraft");
        
        this.player.getPlayer().setScoreboard(this.board);
    }
    
    public void giveStuff()
    {        
        this.armor = RogueCraft.getPlugin().getStuffManager().createArmor(this);
        this.weapon = RogueCraft.getPlugin().getStuffManager().createWeapon(this);
        
        Player p = this.player.getPlayer();
        int[] inventoryCaseHeal = new int[] { 1, 28, 19 };
        int[] inventoryCaseStrenth = new int[] { 2, 29, 20 };
        
        if(this.pClass == PlayerClass.UNKNOW)
        {
            RogueCraft.getPlugin().kickPlayer(p);
        }
        
        p.getInventory().setHelmet(this.armor[0]);
        p.getInventory().setChestplate(this.armor[1]);
        p.getInventory().setLeggings(this.armor[2]);
        p.getInventory().setBoots(this.armor[3]);
        
        p.getInventory().setItem(0, this.weapon);
        
        if(this.weapon.getType() == Material.BOW)
        {
            p.getInventory().setItem(27, new ItemStack(Material.ARROW, 1));
        }
        
        if(this.pStuff.getHealPotion()[0] != 0)
        {
            Potion healPotion = new Potion(PotionType.INSTANT_HEAL);
            healPotion.setLevel(this.pStuff.getHealPotion()[1]);
            
            for(int i = 0; i < this.pStuff.getHealPotion()[0]; i++)
            {
                p.getInventory().setItem(inventoryCaseHeal[i], healPotion.toItemStack(1));
            }
        }
        
        if(this.pStuff.getStrenthPotion()[0] != 0)
        {
            Potion strenthPotion = new Potion(PotionType.STRENGTH);
            strenthPotion.setLevel(this.pStuff.getStrenthPotion()[1]);
            
            for(int i = 0; i < this.pStuff.getStrenthPotion()[0]; i++)
            {
                p.getInventory().setItem(inventoryCaseStrenth[i], strenthPotion.toItemStack(1));
            }
        }
        
        if(this.pStuff.hasBedrockPotion())
        {
            p.getInventory().setItem(7, RogueCraft.getPlugin().getStuffManager().getBedrockPotion());
        }
        
        p.getInventory().setItem(8, new ItemStack(Material.COOKED_BEEF, this.pStuff.getSteak()));
    }
    
    public void repearStuff()
    {
        Player p = this.player.getPlayer();
        
        p.getInventory().setHelmet(this.armor[0]);
        p.getInventory().setChestplate(this.armor[1]);
        p.getInventory().setLeggings(this.armor[2]);
        p.getInventory().setBoots(this.armor[3]);
        p.getInventory().setItem(0, this.weapon);
    }

    public void addCoins(final int c)
    {
        Bukkit.getScheduler().runTaskAsynchronously(RogueCraft.getPlugin(), new Runnable()
        {
            @Override
            public void run()
            {
                coins += CoinsManager.syncCreditJoueur(player.getUniqueId(), c, true, true);
            }
        });
        
        updateScoreboard();
    }
    
    public void addXP(int xp)
    {
        StatsApi.increaseStat(this.player.getUniqueId(), "roguecraft", "xp", xp);
        this.xp += xp;
        updateScoreboard();
    }
    
    public void updateScoreboard()
    {
        this.bar.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Vague:")).setScore(this.arena.getWaveCount());
        this.bar.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Mobs:")).setScore(this.arena.getWave().getMonstersLeft());
        this.bar.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Coins:")).setScore(this.coins);
        this.bar.getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "XP:")).setScore(this.xp);
    }

    public void setRole(Role role)
    {
        this.role = role;
    }
    
    public void setPlayerClass(PlayerClass pClass)
    {
        this.pClass = pClass;
    }
        
    public Player getPlayer()
    {
        return this.player;
    }
    
    public UUID getPlayerID()
    {
        return this.playerId;
    }
    
    public Role getRole()
    {
        return this.role;
    }
        
    public PlayerClass getPlayerClass()
    {
        return this.pClass;
    }

    public PlayerStuff getPlayerStuff()
    {
        return this.pStuff;
    }
    
    public ItemStack[] getArmor()
    {
        return this.armor;
    }
    
    public ItemStack getWeapon()
    {
        return this.weapon;
    }
    
    public boolean hasClass()
    {
        ShardedJedis redis = FastJedis.jedis();
            
        if(redis.exists("roguecraft:properties:" + this.player.getUniqueId()))
        {
            return !redis.get("roguecraft:properties:" + this.player.getUniqueId()).equals("");
        }
        else
        {
            return false;
        }
    }
}
