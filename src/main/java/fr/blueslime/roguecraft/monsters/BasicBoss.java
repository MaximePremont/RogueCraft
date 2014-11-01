package fr.blueslime.roguecraft.monsters;

import fr.blueslime.roguecraft.CustomEntityWither;
import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.utils.ColorUtils;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;

public class BasicBoss extends BasicMonster
{
    private String displayName;
    private int exploadingID;
    
    public BasicBoss(EntityType typeOfMob)
    {
        super(typeOfMob);
        this.displayName = "";
    }
    
    public LivingEntity spawnMob(Arena arena, Location location, int waveCount)
    {
        LivingEntity lEntity;
        if(this.typeOfMob != EntityType.WITHER)
        {
            lEntity = Bukkit.getWorld("world").spawnCreature(location, this.typeOfMob);
        }
        else
        {
            CustomEntityWither playerWither = new CustomEntityWither(((CraftWorld) location.getWorld()).getHandle());
            playerWither.getBukkitEntity().teleport(location);
            
            ((CraftWorld) location.getWorld()).getHandle().addEntity(playerWither, CreatureSpawnEvent.SpawnReason.CUSTOM);
            
            lEntity = (LivingEntity) playerWither.getBukkitEntity();
        }
        
        lEntity.setMaxHealth(this.getCalculatedHealth(waveCount) + 1);
        lEntity.setHealth(this.getCalculatedHealth(waveCount));
        lEntity.setMetadata("RC-MOBUUID", new FixedMetadataValue(RogueCraft.getPlugin(), this.uuid.toString()));
        lEntity.setMetadata("RC-ARENA", new FixedMetadataValue(RogueCraft.getPlugin(), arena.getUUID().toString()));
        lEntity.setMetadata("RC-BOSS", new FixedMetadataValue(RogueCraft.getPlugin(), true));

        if(!this.displayName.equals(""))
            lEntity.setCustomName(ChatColor.GOLD + this.displayName);

        return lEntity;
    }
    
    @Override
    public void onDeath(final Location location)
    {        
        this.exploadingID = Bukkit.getScheduler().scheduleSyncRepeatingTask(RogueCraft.getPlugin(), new Runnable()
        {
            int compteur = 0;
            
            public void run()
            {
                if(compteur == 15)
                {
                    onDeathCallback();
                }
                else
                {
                    Firework fw = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
                    FireworkMeta fwm = fw.getFireworkMeta();

                    Random r = new Random();

                    FireworkEffect.Type type = FireworkEffect.Type.BALL;

                    int r1i = r.nextInt(17) + 1;
                    int r2i = r.nextInt(17) + 1;
                    Color c1 = ColorUtils.getColor(r1i);
                    Color c2 = ColorUtils.getColor(r2i);

                    FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(c1).withFade(c2).with(type).trail(true).build();

                    fwm.addEffect(effect);

                    int rp = r.nextInt(2) + 1;
                    fwm.setPower(rp);

                    fw.setFireworkMeta(fwm);
                    fw.detonate();

                    compteur++;
                }
            }
        }, 2L, 2L);
    }
    
    public void onDeathCallback()
    {
        Bukkit.getScheduler().cancelTask(this.exploadingID);
    }
    
    public double getCalculatedHealth(int waveCount)
    {
        return 300.0D + (0.5 * waveCount);
    }
    
    public double getCalculatedDamage(int waveCount)
    {
        return 5.0D + (1.6 / waveCount);
    }
    
    public void setCustomName(String displayName)
    {
        this.displayName = displayName;
    }
}
