package fr.blueslime.roguecraft.arena;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena.Role;
import fr.blueslime.roguecraft.stuff.PlayerStuff;
import fr.blueslime.roguecraft.stuff.PlayerStuffDeserializer;
import fr.blueslime.roguecraft.stuff.StuffManager.PlayerClass;
import net.samagames.network.client.GamePlayer;
import net.zyuiop.MasterBundle.FastJedis;
import net.zyuiop.coinsManager.CoinsManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import redis.clients.jedis.ShardedJedis;

public class ArenaPlayer
{
    private final GamePlayer player;
    private Role role;
    private Location deathLocation;
    
    private final PlayerStuff pStuff;
    private final ItemStack[] armor;
    private final ItemStack weapon;
    private PlayerClass pClass;
    
    public ArenaPlayer(GamePlayer player)
    {
        this.player = player;
        this.role = Role.PLAYER;
        
        String temp = "{\"helmet\":[1,1,1,1],\"chestplate\":[1,1,1,1],\"leggings\":[1,1,1,1],\"boots\":[1,1,1,1],\"bow\":[1,1,1,1],\"heal-potion\":[1, 1],\"strenth-potion\":[1, 1],\"bedrock-potion\":true,\"steak\":5}";
        
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(PlayerStuff.class, new PlayerStuffDeserializer());
        
        Gson gson = gsonBuilder.create();
        this.pStuff = gson.fromJson(temp, PlayerStuff.class);
        
        this.armor = RogueCraft.getPlugin().getStuffManager().createArmor(this);
        this.weapon = RogueCraft.getPlugin().getStuffManager().createWeapon(this);
    }
    
    public void giveStuff()
    {
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
        
        if(this.pStuff.getHealPotion().length != 0)
        {
            Potion healPotion = new Potion(PotionType.INSTANT_HEAL);
            healPotion.setLevel(this.pStuff.getHealPotion()[1]);
            
            for(int i = 0; i < this.pStuff.getHealPotion()[0]; i++)
            {
                p.getInventory().setItem(inventoryCaseHeal[i], healPotion.toItemStack(1));
            }
        }
        
        if(this.pStuff.getStrenthPotion().length != 0)
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
            ItemStack potion = new ItemStack(Material.POTION, 1);
            
            PotionMeta meta = (PotionMeta) potion.getItemMeta();
            meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Bedrock Potion");
            meta.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 5, 20), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5, 20), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 10, 2), true);
            meta.addCustomEffect(new PotionEffect(PotionEffectType.HUNGER, 20, 2), true);
            
            potion.setItemMeta(meta);
            
            p.getInventory().setItem(7, potion);
        }
        
        p.getInventory().setItem(8, new ItemStack(Material.COOKED_BEEF, this.pStuff.getSteak()));
    }

    public void addCoins(int c)
    {
        CoinsManager.creditJoueur(this.player.getPlayer(), c, true);
    }

    public void setRole(Role role)
    {
        this.role = role;
    }
    
    public void setPlayerClass(PlayerClass pClass)
    {
        this.pClass = pClass;
    }
    
    public void setDeathLocation(Location deathLocation)
    {
        this.deathLocation = deathLocation;
    }
        
    public GamePlayer getPlayer()
    {
        return this.player;
    }
    
    public Role getRole()
    {
        return this.role;
    }
        
    public PlayerClass getPlayerClass()
    {
        return this.pClass;
    }
    
    public Location getDeathLocation()
    {
        return this.deathLocation;
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
            
        if(redis.exists("roguecraft:properties:" + this.player.getPlayerID()))
        {
            return !redis.get("roguecraft:properties:" + this.player.getPlayerID()).equals("");
        }
        else
        {
            return false;
        }
    }
}
