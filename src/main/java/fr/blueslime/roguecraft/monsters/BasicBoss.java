package fr.blueslime.roguecraft.monsters;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.utils.ColorUtils;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public abstract class BasicBoss extends BasicMonster
{
    private int exploadingID;
    
    public BasicBoss(String registeredName, String displayName, double baseHealth, double baseDamage, EntityType typeOfMob)
    {
        super(registeredName, displayName, baseHealth, baseDamage, typeOfMob);
    }
    
    @Override
    public void onDeath(final Location location)
    {
        super.onDeath(location);
        
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

    @Override
    public abstract ItemStack getArmorHelmet();

    @Override
    public abstract ItemStack getArmorChestplate();

    @Override
    public abstract ItemStack getArmorLeggings();

    @Override
    public abstract ItemStack getArmorBoots();

    @Override
    public abstract ItemStack getAtttackWeapon();

}
