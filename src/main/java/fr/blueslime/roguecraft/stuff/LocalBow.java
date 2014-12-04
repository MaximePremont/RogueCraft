package fr.blueslime.roguecraft.stuff;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class LocalBow
{
    private final int powerTier;
    private final int kbTier;
    private final int fireTier;
    
    public LocalBow(int powerTier, int kbTier, int fireTier)
    {
        this.powerTier = powerTier;
        this.kbTier = kbTier;
        this.fireTier = fireTier;
    }
    
    public ItemStack build()
    {
        ItemStack temp = new ItemStack(Material.BOW, 1);
        
        temp.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        temp.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        
        if(this.powerTier != 0)
            temp.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, this.powerTier);
        
        if(this.kbTier != 0)
            temp.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, this.kbTier);
        
        if(this.fireTier != 0)
            temp.addUnsafeEnchantment(Enchantment.ARROW_FIRE, this.fireTier);
        
        return temp;
    }
}
