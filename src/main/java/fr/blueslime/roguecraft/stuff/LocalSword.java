package fr.blueslime.roguecraft.stuff;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/*
 * This file is part of RogueCraft.
 *
 * RogueCraft is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RogueCraft is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with RogueCraft.  If not, see <http://www.gnu.org/licenses/>.
 */
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
