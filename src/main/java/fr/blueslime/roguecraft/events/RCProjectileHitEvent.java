package fr.blueslime.roguecraft.events;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class RCProjectileHitEvent implements Listener
{
    @EventHandler
    public void event(ProjectileHitEvent event)
    {
        if(event.getEntity().hasMetadata("RC-BOSS-PROJECTILE"))
        {
            if(event.getEntity().getType() == EntityType.EGG)
            {
                Location l = event.getEntity().getLocation();
                l.getWorld().createExplosion(l.getX(), l.getY(), l.getZ(), 2, false, false);
            }
        }
    }
}
