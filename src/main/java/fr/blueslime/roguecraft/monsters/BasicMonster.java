package fr.blueslime.roguecraft.monsters;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class BasicMonster implements Cloneable
{
    protected final UUID uuid;
    protected final EntityType typeOfMob;
    private ItemStack armorHelmet, armorChestplate, armorLeggings, armorBoots;
    private ItemStack weapon;
        
    public BasicMonster(EntityType typeOfMob)
    {
        this.uuid = UUID.randomUUID();
        this.typeOfMob = typeOfMob;
    }
    
    public LivingEntity spawnMob(Arena arena, Location location, int waveCount)
    {
        LivingEntity lEntity = location.getWorld().spawnCreature(location, this.typeOfMob);

        double health = this.getCalculatedHealth(waveCount);
        lEntity.setMaxHealth(health + 1);
        lEntity.setHealth(health);
        lEntity.setMetadata("RC-MOBUUID", new FixedMetadataValue(RogueCraft.getPlugin(), this.uuid.toString()));

        if(this.typeOfMob == EntityType.SPIDER || this.typeOfMob == EntityType.PIG_ZOMBIE)
        {
            ((Creature) lEntity).setTarget(arena.getArenaPlayers().get(0).getPlayer().getPlayer());
        }

        EntityEquipment ee = lEntity.getEquipment();

        ee.setHelmet(this.getArmorHelmet());
        ee.setChestplate(this.getArmorChestplate());
        ee.setLeggings(this.getArmorLeggings());
        ee.setBoots(this.getArmorBoots());

        ee.setItemInHand(this.getAtttackWeapon());

        return lEntity;
    }
    
    public void onDeath(Location location) { /** Nothing **/ }

    public void setArmorHelmet(ItemStack armorHelmet)
    {
        this.armorHelmet = armorHelmet;
    }
    
    public void setArmorChestplate(ItemStack armorChestplate)
    {
        this.armorChestplate = armorChestplate;
    }
    
    public void setArmorLeggings(ItemStack armorLeggings)
    {
        this.armorLeggings = armorLeggings;
    }
    
    public void setArmorBoots(ItemStack armorBoots)
    {
        this.armorBoots = armorBoots;
    }
    
    public void setWeapon(ItemStack weapon)
    {
        this.weapon = weapon;
    }
    
    public ItemStack getArmorHelmet()
    {
        return this.armorHelmet;
    }
    
    public ItemStack getArmorChestplate()
    {
        return this.armorChestplate;
    }
    
    public ItemStack getArmorLeggings()
    {
        return this.armorLeggings;
    }
    
    public ItemStack getArmorBoots()
    {
        return this.armorBoots;
    }
    
    public ItemStack getAtttackWeapon()
    {
        return this.weapon;
    } 
    
    public UUID getUniqueIdentifier()
    {
        return this.uuid;
    }
    
    public EntityType getTypeOfMob()
    {
        return this.typeOfMob;
    }
    
    public double getCalculatedHealth(int waveCount)
    {
        return 20.0D + (0.05 * waveCount);
    }

    public double getCalculatedDamage(double baseDamage, int waveCount)
    {
        return baseDamage + (0.025 * waveCount);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    } 
}
