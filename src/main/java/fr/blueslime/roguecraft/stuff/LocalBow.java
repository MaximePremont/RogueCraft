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
