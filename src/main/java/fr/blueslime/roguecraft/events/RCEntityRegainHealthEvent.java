package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class RCEntityRegainHealthEvent implements Listener
{
    @EventHandler
    public void event(EntityRegainHealthEvent event)
    {        
        if(event.getEntity().getType() == EntityType.PLAYER)
        {
            event.setCancelled(true);
            
            Arena arena = RogueCraft.getPlugin().getArena();

            if(arena.hasPlayer(event.getEntity().getUniqueId()))
            {
                event.setCancelled(false);
            }
        }
    }
}
