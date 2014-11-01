package fr.blueslime.roguecraft.randomizer;

import org.bukkit.inventory.ItemStack;

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
