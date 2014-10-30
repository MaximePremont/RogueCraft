package fr.blueslime.roguecraft.randomizer;

import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.monsters.BasicBoss;
import fr.blueslime.roguecraft.monsters.BossNames;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class RandomizerBoss
{
    public BasicBoss randomBoss(Arena arena)
    {
        EntityType type = this.takeRandomEntityType();
        BasicBoss boss = new BasicBoss(type);
        ItemStack[] armor = this.randomArmor(arena.getWaveCount());
        
        boss.setArmorHelmet(armor[0]);
        boss.setArmorChestplate(armor[1]);
        boss.setArmorLeggings(armor[2]);
        boss.setArmorBoots(armor[3]);
        
        if(type == EntityType.ZOMBIE)
        {
            Random rand = new Random();
            int so = rand.nextInt(3) + 1;
            
            ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta headMeta = (SkullMeta) head.getItemMeta();
            
            if(so == 1)
            {
                headMeta.setOwner("cece35b");
                boss.setCustomName("Fantôme de cece35b");
            }
            else if(so == 2)
            {
                headMeta.setOwner("thog92");
                boss.setCustomName("Fantôme de thog92");
            }
            else
            {
                headMeta.setOwner("Aurelien_Sama");
                boss.setCustomName("Fantôme d'Aurelien_Sama");
            }
            
            head.setItemMeta(headMeta);
            boss.setArmorHelmet(head);
        }
        else
        {
            boss.setCustomName(BossNames.randomName());
        }
        
        boss.setWeapon(this.randomWeapon(arena.getWaveCount()));
        
        return boss;
    }
    
    private EntityType takeRandomEntityType()
    {
        Random rand = new Random();
        int so = rand.nextInt(10) + 1;
        
        if(so <= 3)
            return EntityType.ZOMBIE;
        else
            return EntityType.WITHER;
    }
    
    public ItemStack randomWeapon(int waveCount)
    {
        ItemStack weapon = new ItemStack(Material.AIR, 1);   
        return this.applyEnchantments(weapon, this.randomEnchantmentForWeapon(waveCount));
    }
    
    public HashMap<Enchantment, Integer> randomEnchantmentForArmor(int waveCount)
    {
        HashMap<Enchantment, Integer> enchantList = new HashMap<>();
        
        if(waveCount >= 30) { enchantList.put(Enchantment.PROTECTION_ENVIRONMENTAL, 12); }
        else if(waveCount >= 20) { enchantList.put(Enchantment.PROTECTION_ENVIRONMENTAL, 10); }
        else if(waveCount >= 15) { enchantList.put(Enchantment.PROTECTION_ENVIRONMENTAL, 8); }
        else if(waveCount >= 10) { enchantList.put(Enchantment.PROTECTION_ENVIRONMENTAL, 6); }
        else if(waveCount >= 5) { enchantList.put(Enchantment.PROTECTION_ENVIRONMENTAL, 4); }
                
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
            Iterator<Enchantment> keySet = enchantments.keySet().iterator();

            while(keySet.hasNext())
            {
                Enchantment enchantment = keySet.next();
                stack.addUnsafeEnchantment(enchantment, enchantments.get(enchantment));
            }
        }
        
        return stack;
    }
}
