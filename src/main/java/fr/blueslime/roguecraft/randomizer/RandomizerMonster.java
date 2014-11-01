package fr.blueslime.roguecraft.randomizer;

import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.monsters.BasicMonster;
import fr.blueslime.roguecraft.monsters.MonsterTypes;
import fr.blueslime.roguecraft.utils.ItemUtils;
import fr.blueslime.roguecraft.utils.RGB;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
        monster.setWeapon(this.randomWeapon(monster.getTypeOfMob(), arena.getWaveCount()));
        
        return monster;
    }
    
    private EntityType takeRandomEntityType(Arena arena)
    {
        ArrayList<EntityType> types = MonsterTypes.getMonsterAtWave(arena.getWaveCount());
        Collections.shuffle(types, new Random(System.nanoTime()));
        return types.get(0);
    }
    
    public ItemStack randomWeapon(EntityType type, int waveCount)
    {
        ItemStack weapon;
        
        if(type == EntityType.SKELETON)
        {
            weapon = new ItemStack(Material.BOW, 1);
        }
        else
        {
            weapon = new ItemStack(Material.IRON_SWORD, 1);
        }
        
        return this.applyEnchantments(weapon, this.randomEnchantmentForWeapon(weapon.getType(), waveCount));
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
        
        enchantList.put(Enchantment.DURABILITY, 10);
        
        return enchantList;
    }
    
    public HashMap<Enchantment, Integer> randomEnchantmentForWeapon(Material material, int waveCount)
    {
        HashMap<Enchantment, Integer> enchantList = new HashMap<>();
        
        if(material == Material.IRON_SWORD)
        {
            if(waveCount >= 30) { enchantList.put(Enchantment.DAMAGE_ALL, 5); }
            else if(waveCount >= 20) { enchantList.put(Enchantment.DAMAGE_ALL, 4); }
            else if(waveCount >= 15) { enchantList.put(Enchantment.DAMAGE_ALL, 3); }
            else if(waveCount >= 10) { enchantList.put(Enchantment.DAMAGE_ALL, 2); }

            if(waveCount >= 10) { enchantList.put(Enchantment.FIRE_ASPECT, 1); }
        }
        else if(material == Material.BOW)
        {
            if(waveCount >= 30) { enchantList.put(Enchantment.ARROW_DAMAGE, 5); }
            else if(waveCount >= 20) { enchantList.put(Enchantment.ARROW_DAMAGE, 4); }
            else if(waveCount >= 15) { enchantList.put(Enchantment.ARROW_DAMAGE, 3); }
            else if(waveCount >= 10) { enchantList.put(Enchantment.ARROW_DAMAGE, 2); }

            if(waveCount >= 10) { enchantList.put(Enchantment.ARROW_FIRE, 1); }
        }
        
        enchantList.put(Enchantment.DURABILITY, 10);
        
        return enchantList;
    }
    
    public ItemStack[] randomArmor(int waveCount)
    {
        ItemStack[] armor = new ItemStack[4];
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
        if(stack != null)
        {
            for(Enchantment enchantment : enchantments.keySet())
            {
                int lvl = enchantments.get(enchantment);
                stack.addUnsafeEnchantment(enchantment, lvl);
            }
        }
        
        return stack;
    }
}
