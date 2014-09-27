package fr.blueslime.roguecraft.monsters;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Wave;
import java.util.UUID;
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
    private final UUID uuid;
    private final String displayName;
    private final EntityType typeOfMob;
    
    private final double baseHealth;
    private final double baseDamage;
    
    private Wave wave;
        
    public BasicMonster(String displayName, double baseHealth, double baseDamage, EntityType typeOfMob)
    {
        this.uuid = UUID.randomUUID();
        this.displayName = displayName;
        this.baseHealth = baseHealth;
        this.baseDamage = baseDamage;
        this.typeOfMob = typeOfMob;
    }
    
    public LivingEntity spawnMob(Location location, int waveCount)
    {
        LivingEntity lEntity = Bukkit.getWorld("world").spawnCreature(location, this.typeOfMob);

        lEntity.setCustomName(ChatColor.GOLD + this.displayName);
        lEntity.setMaxHealth(this.getCalculatedHealth(waveCount));
        lEntity.setHealth(this.getCalculatedHealth(waveCount));
        lEntity.setMetadata("RC-MOBUUID", new FixedMetadataValue(RogueCraft.getPlugin(), this.uuid.toString()));

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
    
    public EntityType getTypeOfMob()
    {
        return this.typeOfMob;
    }
    
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
