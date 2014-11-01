package fr.blueslime.roguecraft.stuff;

import fr.blueslime.roguecraft.arena.ArenaPlayer;
import fr.blueslime.roguecraft.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StuffManager
{
    public static enum PlayerClass { ARCHER, KNIGHT, WIZARD, UNKNOW }

    public ItemStack[] createArmor(ArenaPlayer player)
    {
        PlayerStuff stuff = player.getPlayerStuff();
        ItemStack[] temp = new ItemStack[4];

        temp[0] = new LocalArmorPiece(1, stuff.getHelmet()[0], stuff.getHelmet()[1], stuff.getHelmet()[2], stuff.getHelmet()[3]).build();
        temp[1] = new LocalArmorPiece(2, stuff.getChestplate()[0], stuff.getChestplate()[1], stuff.getChestplate()[2], stuff.getChestplate()[3]).build();
        temp[2] = new LocalArmorPiece(3, stuff.getLeggings()[0], stuff.getLeggings()[1], stuff.getLeggings()[2], stuff.getLeggings()[3]).build();
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
            player.setPlayerClass(PlayerClass.UNKNOW);
            return new ItemStack(Material.GLASS, 1);
        }
    }
    
    public ItemStack getBedrockPotion()
    {
        ItemStack potion = new ItemStack(Material.POTION, 1);
            
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Bedrock Potion");
        meta.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 100, 5), true);
        meta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 5), true);
        meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2), true);
        meta.addCustomEffect(new PotionEffect(PotionEffectType.HUNGER, 150, 2), true);

        potion.setItemMeta(meta);
        
        return potion;
    }
    
    public boolean dropItem(ArenaPlayer player, ItemStack stack)
    {
        if(player != null)
        {
            if(ItemUtils.isArmor(stack))
                return false;
            
            if(player.getPlayerClass() == PlayerClass.ARCHER && stack.getType() == Material.BOW)
                return false;

            else if(player.getPlayerClass() == PlayerClass.KNIGHT && stack.getType() == Material.IRON_SWORD)
                return false;

            else if(player.getPlayerClass() == PlayerClass.WIZARD && stack.getType() == Material.BLAZE_ROD)
                return false;
        }
        else
        {
            return false;
        }
        
        return true;
    }
}
