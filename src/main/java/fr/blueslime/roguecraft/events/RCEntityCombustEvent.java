package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

public class RCEntityCombustEvent implements Listener
{
    @EventHandler
    public void event(EntityCombustEvent event)
    {
        if(event.getEntity().getType() == EntityType.PLAYER)
        {
            if(!RogueCraft.getPlugin().getArena().hasPlayer(event.getEntity().getUniqueId()))
                event.setCancelled(true);
        }
    }
}
