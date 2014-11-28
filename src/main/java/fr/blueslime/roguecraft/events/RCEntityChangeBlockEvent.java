package fr.blueslime.roguecraft.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class RCEntityChangeBlockEvent implements Listener
{
    @EventHandler
    public void event(EntityChangeBlockEvent event)
    {
        event.setCancelled(true);
    }
}
