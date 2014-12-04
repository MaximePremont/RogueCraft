package fr.blueslime.roguecraft.stuff;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class LocalSword
{    
    private final int sharpnessTier;
    private final int kbTier;
    private final int fireTier;
    
    public LocalSword(int sharpnessTier, int kbTier, int fireTier)
    {
        this.sharpnessTier = sharpnessTier;
        this.kbTier = kbTier;
        this.fireTier = fireTier;
    }
    
    public ItemStack build()
    {
        ItemStack temp = new ItemStack(Material.IRON_SWORD, 1);
        
        temp.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        
        if(this.sharpnessTier != 0)
            temp.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, this.sharpnessTier);
        
        if(this.kbTier != 0)
            temp.addUnsafeEnchantment(Enchantment.KNOCKBACK, this.kbTier);
        
        if(this.fireTier != 0)
            temp.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, this.fireTier);
        
        return temp;
    }
}
