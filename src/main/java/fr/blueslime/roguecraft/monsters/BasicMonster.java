package fr.blueslime.roguecraft.monsters;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Wave;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public abstract class BasicMonster implements Cloneable
{
    private final String displayName;
    private final EntityType typeOfMob;
    
    private final double baseHealth;
    private final double baseDamage;
    
    private Wave wave;
        
    public BasicMonster(String displayName, double baseHealth, double baseDamage, EntityType typeOfMob)
    {
        this.displayName = displayName;
        this.baseHealth = baseHealth;
        this.baseDamage = baseDamage;
        this.typeOfMob = typeOfMob;
    }
    
    public LivingEntity spawnMob(Wave wave, Location location, int monsterLevel)
    {
        LivingEntity lEntity = Bukkit.getWorld("world").spawnCreature(location, this.typeOfMob);

        lEntity.setCustomName(ChatColor.GOLD + this.displayName);
        lEntity.setMaxHealth(this.getCalculatedHealth(monsterLevel));
        lEntity.setHealth(this.getCalculatedHealth(monsterLevel));
        lEntity.setMetadata("RC-MONSTERLEVEL", new FixedMetadataValue(RogueCraft.getPlugin(), monsterLevel));

        EntityEquipment ee = lEntity.getEquipment();

        ee.setHelmet(this.getArmorHelmet());
        ee.setChestplate(this.getArmorChestplate());
        ee.setLeggings(this.getArmorLeggings());
        ee.setBoots(this.getArmorBoots());
  
        ee.setItemInHand(this.getAtttackWeapon());

        return lEntity;
    }
    
    public void onDeath(Location location)
    {
        this.wave.monsterKilled();
    }
    
    public abstract ItemStack getArmorHelmet();
    public abstract ItemStack getArmorChestplate();
    public abstract ItemStack getArmorLeggings();
    public abstract ItemStack getArmorBoots();
    
    public abstract ItemStack getAtttackWeapon();
    
    public String getDisplayName()
    {
        return this.displayName;
    }
    
    public double getBaseHealth()
    {
        return this.baseHealth;
    }
    
    public double getBaseDamage()
    {
        return this.baseDamage;
    }
    
    public double getCalculatedHealth(int monsterLevel)
    {
        return this.baseHealth * (1.2 * monsterLevel);
    }

    public double getCalculatedDamage(int monsterLevel)
    {
        return this.baseDamage * (1.1 * monsterLevel);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    } 
}
