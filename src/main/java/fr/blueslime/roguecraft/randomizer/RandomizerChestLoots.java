package fr.blueslime.roguecraft.randomizer;

import fr.blueslime.roguecraft.RogueCraft;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class RandomizerChestLoots
{
    private final ArrayList<RandomItem> items;
    
    public RandomizerChestLoots()
    {
        this.items = new ArrayList<>();
        
        ItemStack fishingSwag = new ItemStack(Material.FISHING_ROD);
        fishingSwag.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
        fishingSwag.addUnsafeEnchantment(Enchantment.KNOCKBACK, 3);
        ItemMeta fishingSwagMeta = fishingSwag.getItemMeta();
        fishingSwagMeta.setDisplayName(ChatColor.GOLD + "--[ Canne à Swag ]--");
        fishingSwag.setItemMeta(fishingSwagMeta);
        
        ItemStack wool = new ItemStack(Material.WOOL, 1);
        wool.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
        ItemMeta woolMeta = wool.getItemMeta();
        woolMeta.setDisplayName(ChatColor.BLUE + "--[ Stainte Laine ]--");
        wool.setItemMeta(woolMeta);
        
        this.registerItem(new RandomItem(new ItemStack(Material.WEB), 1, 300));
        this.registerItem(new RandomItem(new ItemStack(Material.FLINT), 1, 300));
        this.registerItem(new RandomItem(new ItemStack(Material.COOKED_CHICKEN), 1, 550));
        this.registerItem(new RandomItem(new ItemStack(Material.COOKED_BEEF), 1, 600));
        this.registerItem(new RandomItem(new ItemStack(Material.RAW_BEEF), 1, 300));
        
        this.registerItem(new RandomItem(new Potion(PotionType.STRENGTH).toItemStack(1), 2, 200));
        this.registerItem(new RandomItem(new Potion(PotionType.REGEN).toItemStack(1), 2, 200));
        this.registerItem(new RandomItem(new Potion(PotionType.FIRE_RESISTANCE).splash().toItemStack(1), 2, 300));
        this.registerItem(new RandomItem(new Potion(PotionType.NIGHT_VISION).splash().toItemStack(1), 2, 200));
        this.registerItem(new RandomItem(new Potion(PotionType.INSTANT_HEAL, 2).toItemStack(1), 2, 300));
        this.registerItem(new RandomItem(new Potion(PotionType.SPEED).toItemStack(1), 2, 300));
        this.registerItem(new RandomItem(new Potion(PotionType.INSTANT_HEAL).toItemStack(1), 2, 300));
        this.registerItem(new RandomItem(new Potion(PotionType.INSTANT_HEAL).splash().toItemStack(1), 2, 300));
        
        this.registerItem(new RandomItem(wool, 3, 10));
        this.registerItem(new RandomItem(RogueCraft.getPlugin().getStuffManager().getBedrockPotion(), 3, 10));
        this.registerItem(new RandomItem(fishingSwag, 3, 100));
        this.registerItem(new RandomItem(new ItemStack(Material.GOLDEN_APPLE), 3, 100));
        this.registerItem(new RandomItem(new ItemStack(Material.ENDER_PEARL), 3, 200));
    }

    public HashMap<Integer, ItemStack> randomLootsInSlots(boolean special)
    {
        HashMap<Integer, ItemStack> stacks = new HashMap<>();
        ArrayList<Integer> alreadyUsed = new ArrayList<>();
        Random rand = new Random();
        
        for(int i = 0; i < 5; i++)
        {
            ItemStack stack = getItemStackByRandom(special);
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
    
    private void registerItem(RandomItem item)
    {
        this.items.add(item);
    }
    
    private ItemStack getItemStackByRandom(boolean special)
    {        
        Random rand = new Random();
        
        for(RandomItem item : this.items)
        {
            int value = this.getValueByRandom(special);
                
            if(item.getValue() == value)
            {
                int freq = rand.nextInt(700);

                if(freq != 0 && freq <= item.getChance())
                {
                    return item.getStack();
                }
            }
        }
        
        return new ItemStack(Material.AIR, 1);
    }
    
    private int getValueByRandom(boolean special)
    {
        Random rand = new Random();
        int result = rand.nextInt(3000);
        
        if(!special)
        {
            if(result <= 1500) return 1;
            else if(result > 1500 && result <= 1900) return 2;
            else return 3;
        }
        else
        {
            return 3;
        }
    }
}
