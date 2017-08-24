package fr.blueslime.roguecraft.randomizer;

import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.monsters.boss.BasicBoss;
import fr.blueslime.roguecraft.monsters.boss.BossList;
import java.util.HashMap;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

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
public class RandomizerBoss
{
    public BasicBoss randomBoss(Arena arena)
    {
        BossList bossType = BossList.randomBoss();
        BasicBoss boss = bossType.create();
        ItemStack[] armor = this.randomArmor(arena.getWaveCount());
        
        if(boss.hasCustomHead())
        {            
            ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta headMeta = (SkullMeta) head.getItemMeta();
            headMeta.setOwner(boss.getCustomHead());
            head.setItemMeta(headMeta);
            boss.setArmorHelmet(head);
        }
        else
        {
            boss.setArmorHelmet(armor[0]);
        }
        
        boss.setArmorChestplate(armor[1]);
        boss.setArmorLeggings(armor[2]);
        boss.setArmorBoots(armor[3]);
        boss.setWeapon(this.randomWeapon(arena.getWaveCount()));
        
        return boss;
    }
    
    public ItemStack randomWeapon(int waveCount)
    {
        ItemStack weapon = new ItemStack(Material.IRON_SWORD, 1);   
        return this.applyEnchantments(weapon, this.randomEnchantmentForWeapon(waveCount));
    }
    
    public HashMap<Enchantment, Integer> randomEnchantmentForArmor(int waveCount)
    {
        HashMap<Enchantment, Integer> enchantList = new HashMap<>();
        
        if(waveCount >= 30) { enchantList.put(Enchantment.PROTECTION_ENVIRONMENTAL, 12); }
        else if(waveCount >= 20) { enchantList.put(Enchantment.PROTECTION_ENVIRONMENTAL, 9); }
        else if(waveCount >= 15) { enchantList.put(Enchantment.PROTECTION_ENVIRONMENTAL, 7); }
        else if(waveCount >= 10) { enchantList.put(Enchantment.PROTECTION_ENVIRONMENTAL, 5); }
        else if(waveCount >= 5) { enchantList.put(Enchantment.PROTECTION_ENVIRONMENTAL, 3); }
                
        if(waveCount >= 10) { enchantList.put(Enchantment.THORNS, 1); }
        else if(waveCount >= 20) { enchantList.put(Enchantment.THORNS, 2); }
        else if(waveCount >= 30) { enchantList.put(Enchantment.THORNS, 3); }
        
        enchantList.put(Enchantment.DURABILITY, 10);
        
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
        
        enchantList.put(Enchantment.DURABILITY, 10);
        
        return enchantList;
    }
    
    public ItemStack[] randomArmor(int waveCount)
    {
        ItemStack[] armor = new ItemStack[4];
        HashMap<Enchantment, Integer> enchants = this.randomEnchantmentForArmor(waveCount);
        
        armor[0] = this.applyEnchantments(new ItemStack(Material.DIAMOND_HELMET, 1), enchants);
        armor[1] = this.applyEnchantments(new ItemStack(Material.DIAMOND_CHESTPLATE, 1), enchants);
        armor[2] = this.applyEnchantments(new ItemStack(Material.DIAMOND_LEGGINGS, 1), enchants);
        armor[3] = this.applyEnchantments(new ItemStack(Material.DIAMOND_BOOTS, 1), enchants);
        
        return armor;
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
