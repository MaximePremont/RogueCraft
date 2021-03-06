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
public class LocalArmorPiece
{
    private final int piece;
    private final int tier;
    private final int pTier;
    private final int firePTier;
    private final int blastPTier;
    
    public LocalArmorPiece(int piece, int tier, int pTier, int firePTier, int blastPTier)
    {
        this.piece = piece;
        this.tier = tier;
        this.pTier = pTier;
        this.firePTier = firePTier;
        this.blastPTier = blastPTier;
    }
    
    public ItemStack build()
    {
        ItemStack temp = new ItemStack(this.determineMaterial(), 1);
        
        temp.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        
        if(this.pTier != 0)
            temp.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, this.pTier);
        
        if(this.firePTier != 0)
            temp.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, this.firePTier);
        
        if(this.blastPTier != 0)
            temp.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, this.blastPTier);
        
        return temp;
    }
    
    public Material determineMaterial()
    {
        switch(piece)
        {
            case 1:
                switch(tier)
                {
                    case 1: return Material.LEATHER_HELMET;
                    case 2: return Material.CHAINMAIL_HELMET;
                    case 3: return Material.IRON_HELMET;
                    case 4: return Material.GOLD_HELMET;
                    case 5: return Material.DIAMOND_HELMET;
                }
            
            case 2:
                switch(tier)
                {
                    case 1: return Material.LEATHER_CHESTPLATE;
                    case 2: return Material.CHAINMAIL_CHESTPLATE;
                    case 3: return Material.IRON_CHESTPLATE;
                    case 4: return Material.GOLD_CHESTPLATE;
                    case 5: return Material.DIAMOND_CHESTPLATE;
                }
            
            case 3:
                switch(tier)
                {
                    case 1: return Material.LEATHER_LEGGINGS;
                    case 2: return Material.CHAINMAIL_LEGGINGS;
                    case 3: return Material.IRON_LEGGINGS;
                    case 4: return Material.GOLD_LEGGINGS;
                    case 5: return Material.DIAMOND_LEGGINGS;
                }
            
            case 4:
                switch(tier)
                {
                    case 1: return Material.LEATHER_BOOTS;
                    case 2: return Material.CHAINMAIL_BOOTS;
                    case 3: return Material.IRON_BOOTS;
                    case 4: return Material.GOLD_BOOTS;
                    case 5: return Material.DIAMOND_BOOTS;
                }
        }
        
        return Material.AIR;
    }
}
