package fr.blueslime.roguecraft.stuff;

import fr.blueslime.roguecraft.arena.ArenaPlayer;
import fr.blueslime.roguecraft.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.json.JSONArray;
import org.json.JSONObject;

public class StuffManager
{
    public static enum PlayerClass { ARCHER, KNIGHT, WIZARD }

    public ItemStack[] createArmor(ArenaPlayer player)
    {
        PlayerStuff stuff = player.getPlayerStuff();
        ItemStack[] temp = new ItemStack[3];

        temp[0] = new LocalArmorPiece(1, stuff.getHelmet()[0], stuff.getHelmet()[1], stuff.getHelmet()[2], stuff.getHelmet()[3]).build();
        temp[1] = new LocalArmorPiece(2, stuff.getChestplate()[0], stuff.getChestplate()[1], stuff.getChestplate()[2], stuff.getChestplate()[3]).build();
        temp[2] = new LocalArmorPiece(3, stuff.getLegging()[0], stuff.getLegging()[1], stuff.getLegging()[2], stuff.getLegging()[3]).build();
        temp[3] = new LocalArmorPiece(4, stuff.getBoots()[0], stuff.getBoots()[1], stuff.getBoots()[2], stuff.getBoots()[3]).build();
        
        return temp;
    }
    
    public ItemStack createWeapon(ArenaPlayer player)
    {
        PlayerStuff stuff = player.getPlayerStuff();
        
        if(stuff.getBow() != null)
        {
            player.setPlayerClass(PlayerClass.ARCHER);
            return new LocalBow(stuff.getBow()[0], stuff.getBow()[1], stuff.getBow()[2], stuff.getBow()[3]).build();
        }
        else if(stuff.getSword() != null)
        {
            player.setPlayerClass(PlayerClass.KNIGHT);
            return new LocalSword(stuff.getSword()[0], stuff.getSword()[0], stuff.getSword()[0], stuff.getSword()[0]).build();
        }
        else if(stuff.getWand() != null)
        {
            player.setPlayerClass(PlayerClass.WIZARD);
            return new LocalWand(stuff.getWand()[0], stuff.getWand()[1]).build();
        }
        else
        {
            return new ItemStack(Material.GLASS, 1);
        }
    }
    
    public boolean dropItem(ArenaPlayer player, ItemStack stack)
    {
        if(ItemUtils.isArmor(stack))
            return false;
        
        if(player.getPlayerClass() == PlayerClass.ARCHER && stack.getType() == Material.BOW)
            return false;
        
        else if(player.getPlayerClass() == PlayerClass.KNIGHT && stack.getType() == Material.IRON_SWORD)
            return false;
        
        else if(player.getPlayerClass() == PlayerClass.WIZARD && stack.getType() == Material.BLAZE_ROD)
            return false;
        
        return true;
    }
}
