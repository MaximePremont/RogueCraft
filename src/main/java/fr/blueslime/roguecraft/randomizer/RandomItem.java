package fr.blueslime.roguecraft.randomizer;

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
public class RandomItem
{
    private final ItemStack stack;
    private final int value;
    private final int chance;
    
    public RandomItem(ItemStack stack, int value, int chance)
    {
        this.stack = stack;
        this.value = value;
        this.chance = chance;
    }
    
    public ItemStack getStack()
    {
        return this.stack;
    }
    
    public int getValue()
    {
        return this.value;
    }
    
    public int getChance()
    {
        return this.chance;
    }
}
