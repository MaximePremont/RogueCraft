package fr.blueslime.roguecraft.stuff;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class LocalBow
{
    public static enum BowType { BASIC, POISONOUS, FREEZING }
    
    private final BowType type;
    private final int powerTier;
    private final int kbTier;
    private final int fireTier;
    
    public LocalBow(int type, int powerTier, int kbTier, int fireTier)
    {
        switch(type)
        {
            case 1:
                this.type = BowType.BASIC;
                break;
                
            case 2:
                this.type = BowType.POISONOUS;
                break;
                
            case 3:
                this.type = BowType.FREEZING;
                break;
                
            default:
                this.type = BowType.BASIC;
                break;
        }
        
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
        
        switch(this.type)
        {
            case BASIC:
                temp.getItemMeta().setDisplayName("Arc basique");
                break;
                
            case POISONOUS:
                temp.getItemMeta().setDisplayName("Arc empoisonné");
                break;
                
            case FREEZING:
                temp.getItemMeta().setDisplayName("Arc de glace");
                break;
        }
        
        return temp;
    }
    
    public BowType getBowType()
    {
        return this.type;
    }
}
