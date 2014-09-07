package fr.blueslime.roguecraft.monsters;

import fr.blueslime.roguecraft.arena.Wave;
import fr.blueslime.roguecraft.utils.ItemUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TestMob extends BasicMonster
{
    public TestMob(String registeredName, String displayName, double baseHealth, double baseDamage, EntityType typeOfMob)
    {
        super(registeredName, displayName, baseHealth, baseDamage, typeOfMob);
    }

    @Override
    public LivingEntity spawnMob(Wave wave, Location location, int monsterLevel)
    {
        LivingEntity entity = super.spawnMob(wave, location, monsterLevel);
        
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
        
        return entity;
    }
    
    @Override
    public ItemStack getArmorHelmet()
    {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setOwner("cece35b");
        head.setItemMeta(headMeta);
        
        return head;
    }

    @Override
    public ItemStack getArmorChestplate()
    {
        ItemStack stack = ItemUtils.setColor(new ItemStack(Material.LEATHER_CHESTPLATE, 1), 255, 255, 255);
        stack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        
        return stack;
    }

    @Override
    public ItemStack getArmorLeggings()
    {
        ItemStack stack = ItemUtils.setColor(new ItemStack(Material.LEATHER_LEGGINGS, 1), 255, 255, 255);
        stack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        
        return stack;
    }

    @Override
    public ItemStack getArmorBoots()
    {
        ItemStack stack = ItemUtils.setColor(new ItemStack(Material.LEATHER_BOOTS, 1), 255, 255, 255);
        stack.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        
        return stack;
    }

    @Override
    public ItemStack getAtttackWeapon()
    {
        ItemStack stack = new ItemStack(Material.COOKIE, 1);
        stack.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        
        return stack;
    }
}
