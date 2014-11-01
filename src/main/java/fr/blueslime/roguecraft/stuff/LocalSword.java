package fr.blueslime.roguecraft.stuff;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class LocalSword
{
    public static enum SwordType { BASIC, POISONOUS, FREEZING }
    
    private final SwordType type;
    private final int sharpnessTier;
    private final int kbTier;
    private final int fireTier;
    
    public LocalSword(int type, int sharpnessTier, int kbTier, int fireTier)
    {
        switch(type)
        {
            case 1:
                this.type = SwordType.BASIC;
                break;
                
            case 2:
                this.type = SwordType.POISONOUS;
                break;
                
            case 3:
                this.type = SwordType.FREEZING;
                break;
                
            default:
                this.type = SwordType.BASIC;
                break;
        }
        
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
        
        switch(this.type)
        {
            case BASIC:
                temp.getItemMeta().setDisplayName("Epée basique");
                break;
                
            case POISONOUS:
                temp.getItemMeta().setDisplayName("Epée empoisonnée");
                break;
                
            case FREEZING:
                temp.getItemMeta().setDisplayName("Epée de glace");
                break;
        }
        
        return temp;
    }
    
    public SwordType getSwordType()
    {
        return this.type;
    }
}
