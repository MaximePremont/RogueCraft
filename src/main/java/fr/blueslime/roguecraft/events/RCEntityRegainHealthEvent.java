package fr.blueslime.roguecraft.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class RCEntityRegainHealthEvent implements Listener
{
    @EventHandler
    public void event(EntityRegainHealthEvent event)
    {
        event.setCancelled(true);
    }
}
