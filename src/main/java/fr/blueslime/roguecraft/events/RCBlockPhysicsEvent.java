package fr.blueslime.roguecraft.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class RCBlockPhysicsEvent implements Listener
{
    @EventHandler
    public void event(BlockPhysicsEvent event)
    {
        event.setCancelled(true);
    }
}
