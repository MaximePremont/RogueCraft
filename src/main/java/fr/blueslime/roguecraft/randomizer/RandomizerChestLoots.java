package fr.blueslime.roguecraft.randomizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class RandomizerChestLoots
{
    public HashMap<Integer, ItemStack> randomLootsInSlots()
    {
        HashMap<Integer, ItemStack> stacks = new HashMap<>();
        ArrayList<Integer> alreadyUsed = new ArrayList<>();
        Random rand = new Random();
        
        for(int i = 0; i < 5; i++)
        {
            ItemStack stack = getItemStackByRandom(rand.nextInt(211));
            int slot = rand.nextInt(27);
            
            while(true)
            {
                if(alreadyUsed.contains(slot))
                {
                    slot = rand.nextInt(27);
                }
                else
                {
                    break;
                }
            }
            
            alreadyUsed.add(slot);
            stacks.put(slot, stack);
        }
        
        return stacks;
    }
    
    private ItemStack getItemStackByRandom(int i)
    {
        Random rand = new Random();
        
        if(i <= 80) return new ItemStack(Material.COOKED_BEEF, rand.nextInt(3));
        else if(i <= 120) return buildPotion(PotionType.INSTANT_HEAL, 1, 1);
        else if(i <= 150) return buildPotion(PotionType.INSTANT_HEAL, 2, 1);
        else if(i <= 190) return buildPotion(PotionType.STRENGTH, 1, 1);
        else if(i <= 210) return buildPotion(PotionType.STRENGTH, 2, 1);
        else if(i <= 211) return new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1);
        else return new ItemStack(Material.BOW, 1);
    }
    
    private ItemStack buildPotion(PotionType type, int level, int amount)
    {
        Potion potion = new Potion(type);
        potion.setLevel(level);
        
        return potion.toItemStack(amount);
    }
}
