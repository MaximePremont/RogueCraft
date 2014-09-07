package fr.blueslime.roguecraft.arena;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena.Role;
import fr.blueslime.roguecraft.stuff.PlayerStuff;
import fr.blueslime.roguecraft.stuff.PlayerStuffDeserializer;
import fr.blueslime.roguecraft.stuff.StuffManager.PlayerClass;
import net.zyuiop.coinsManager.CoinsManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArenaPlayer
{
    private final VirtualPlayer player;
    private Role role;
    private Location deathLocation;
    
    private PlayerClass pClass;
    private PlayerStuff pStuff;
    private ItemStack[] armor;
    private ItemStack weapon;
    
    public ArenaPlayer(VirtualPlayer player)
    {
        this.player = player;
        this.role = Role.PLAYER;
        
        String temp = "{\"helmet\":[1,1,1,1],\"chestplate\":[1,1,1,1],\"leggings\":[1,1,1,1],\"boots\":[1,1,1,1],\"bow\":[1,1,1,1],\"heal-potion\":[1, 1],\"strenth-potion\":[1, 1],\"bedrock-potion\":true,\"steak\":10}";
        
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
        
        p.getInventory().setHelmet(this.armor[0]);
        p.getInventory().setChestplate(this.armor[1]);
        p.getInventory().setLeggings(this.armor[2]);
        p.getInventory().setBoots(this.armor[3]);
        
        p.getInventory().setItem(0, this.weapon);
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
        
    public VirtualPlayer getPlayer()
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
}
