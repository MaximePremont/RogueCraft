package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class RCEntityDeathEvent implements Listener
{
    @EventHandler
    public void event(EntityDeathEvent event)
    {
        LivingEntity damaged = (LivingEntity) event.getEntity();
        event.getDrops().clear();
            
        if(damaged.hasMetadata("RC-ARENA"))
        {
            Arena arena = RogueCraft.getPlugin().getArena();

            if(damaged.isDead())
            {
                if(damaged.hasMetadata("RC-BOSS"))
                {
                    arena.getWave().getBoss().onDeath(event.getEntity().getLocation());
                }
                
                arena.getWave().monsterKilled();
            }
        }
    }
}
