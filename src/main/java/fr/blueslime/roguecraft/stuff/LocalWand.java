package fr.blueslime.roguecraft.stuff;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class LocalWand
{    
    private final int sharpnessTier;
    
    public LocalWand(int sharpnessTier)
    {
        this.sharpnessTier = sharpnessTier;
    }
    
    public ItemStack build()
    {
        ItemStack temp = new ItemStack(Material.BLAZE_ROD, 1);
                
        if(this.sharpnessTier != 0)
            temp.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, this.sharpnessTier);
        
        return temp;
    }
}
