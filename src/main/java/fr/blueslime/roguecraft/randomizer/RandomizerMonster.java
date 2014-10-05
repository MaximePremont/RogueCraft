package fr.blueslime.roguecraft.randomizer;

import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.monsters.BasicMonster;
import fr.blueslime.roguecraft.monsters.MonsterTypes;
import fr.blueslime.roguecraft.utils.ItemUtils;
import fr.blueslime.roguecraft.utils.RGB;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class RandomizerMonster
{
    public BasicMonster randomMonster(Arena arena)
    {
        BasicMonster monster = new BasicMonster(this.takeRandomEntityType(arena));
        ItemStack[] armor = this.randomArmor(arena.getWaveCount());
        
        monster.setArmorHelmet(armor[0]);
        monster.setArmorChestplate(armor[1]);
        monster.setArmorLeggings(armor[2]);
        monster.setArmorBoots(armor[3]);
        monster.setWeapon(this.randomWeapon(arena.getWaveCount()));
        
        return monster;
    }
    
    private EntityType takeRandomEntityType(Arena arena)
    {
        Random rand = new Random();
        ArrayList<EntityType> types = MonsterTypes.getMonsterAtWave(arena.getWaveCount());
        
        Collections.shuffle(types, new Random(System.nanoTime()));
        
        int index = rand.nextInt(types.size());
        return types.get(index);
    }
    
    public ItemStack randomWeapon(int waveCount)
    {
        ItemStack weapon = new ItemStack(Material.AIR, 1);   
        return this.applyEnchantments(weapon, this.randomEnchantmentForWeapon(waveCount));
    }
    
    public HashMap<Enchantment, Integer> randomEnchantmentForArmor(int waveCount)
    {
        HashMap<Enchantment, Integer> enchantList = new HashMap<>();
        
        if(waveCount >= 30) { enchantList.put(Enchantment.PROTECTION_ENVIRONMENTAL, 5); }
        else if(waveCount >= 20) { enchantList.put(Enchantment.PROTECTION_ENVIRONMENTAL, 4); }
        else if(waveCount >= 15) { enchantList.put(Enchantment.PROTECTION_ENVIRONMENTAL, 3); }
        else if(waveCount >= 10) { enchantList.put(Enchantment.PROTECTION_ENVIRONMENTAL, 2); }
        else if(waveCount >= 5) { enchantList.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1); }
                
        if(waveCount >= 10) { enchantList.put(Enchantment.THORNS, 1); }
        else if(waveCount >= 20) { enchantList.put(Enchantment.THORNS, 2); }
        else if(waveCount >= 30) { enchantList.put(Enchantment.THORNS, 3); }
        
        enchantList.put(Enchantment.DURABILITY, Integer.MAX_VALUE);
        
        return enchantList;
    }
    
    public HashMap<Enchantment, Integer> randomEnchantmentForWeapon(int waveCount)
    {
        HashMap<Enchantment, Integer> enchantList = new HashMap<>();
        
        if(waveCount >= 30) { enchantList.put(Enchantment.DAMAGE_ALL, 5); }
        else if(waveCount >= 20) { enchantList.put(Enchantment.DAMAGE_ALL, 4); }
        else if(waveCount >= 15) { enchantList.put(Enchantment.DAMAGE_ALL, 3); }
        else if(waveCount >= 10) { enchantList.put(Enchantment.DAMAGE_ALL, 2); }
        else if(waveCount >= 5) { enchantList.put(Enchantment.DAMAGE_ALL, 1); }
        
        if(waveCount >= 10) { enchantList.put(Enchantment.FIRE_ASPECT, 1); }
        
        if(waveCount >= 10) { enchantList.put(Enchantment.KNOCKBACK, 1); }
        else if(waveCount >= 20) { enchantList.put(Enchantment.KNOCKBACK, 2); }
        else if(waveCount >= 30) { enchantList.put(Enchantment.KNOCKBACK, 3); }
        
        enchantList.put(Enchantment.DURABILITY, Integer.MAX_VALUE);
        
        return enchantList;
    }
    
    public ItemStack[] randomArmor(int waveCount)
    {
        ItemStack[] armor = new ItemStack[3];
        RGB color = this.randomColor();
        HashMap<Enchantment, Integer> enchants = this.randomEnchantmentForArmor(waveCount);
        
        armor[0] = this.applyEnchantments(ItemUtils.setColor(new ItemStack(Material.LEATHER_HELMET, 1), color.getRed(), color.getGreen(), color.getBlue()), enchants);
        armor[1] = this.applyEnchantments(ItemUtils.setColor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), color.getRed(), color.getGreen(), color.getBlue()), enchants);
        armor[2] = this.applyEnchantments(ItemUtils.setColor(new ItemStack(Material.LEATHER_LEGGINGS, 1), color.getRed(), color.getGreen(), color.getBlue()), enchants);
        armor[3] = this.applyEnchantments(ItemUtils.setColor(new ItemStack(Material.LEATHER_BOOTS, 1), color.getRed(), color.getGreen(), color.getBlue()), enchants);
        
        return armor;
    }
    
    public RGB randomColor()
    {
        Random rand = new Random();
        
        int r = rand.nextInt(255) + 1;
        int g = rand.nextInt(255) + 1;
        int b = rand.nextInt(255) + 1;
        
        return new RGB(r, g, b);
    }
    
    private ItemStack applyEnchantments(ItemStack stack, HashMap<Enchantment, Integer> enchantments)
    {
        Iterator<Enchantment> keySet = enchantments.keySet().iterator();
        
        while(keySet.hasNext())
        {
            Enchantment enchantment = keySet.next();
            stack.addEnchantment(enchantment, enchantments.get(enchantment));
        }
        
        return stack;
    }
}
