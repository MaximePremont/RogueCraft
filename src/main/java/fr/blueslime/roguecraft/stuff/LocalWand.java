package fr.blueslime.roguecraft.stuff;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class LocalWand
{
    public static enum WandType { BASIC, STRENTH, HEALING }
    
    private final WandType type;
    private final int sharpnessTier;
    
    public LocalWand(int type, int sharpnessTier)
    {
        switch(type)
        {
            case 1:
                this.type = WandType.BASIC;
                break;
                
            case 2:
                this.type = WandType.STRENTH;
                break;
                
            case 3:
                this.type = WandType.HEALING;
                break;
                
            default:
                this.type = WandType.BASIC;
                break;
        }
        
        this.sharpnessTier = sharpnessTier;
    }
    
    public ItemStack build()
    {
        ItemStack temp = new ItemStack(Material.BLAZE_ROD, 1);
                
        if(this.sharpnessTier != 0)
            temp.addEnchantment(Enchantment.DAMAGE_ALL, this.sharpnessTier);
        
        switch(this.type)
        {
            case BASIC:
                temp.getItemMeta().setDisplayName("Baguette basique");
                break;
                
            case STRENTH:
                temp.getItemMeta().setDisplayName("Baguette de force");
                break;
                
            case HEALING:
                temp.getItemMeta().setDisplayName("Baguette de santé");
                break;
        }
        
        return temp;
    }
    
    public WandType getBowType()
    {
        return this.type;
    }
}
