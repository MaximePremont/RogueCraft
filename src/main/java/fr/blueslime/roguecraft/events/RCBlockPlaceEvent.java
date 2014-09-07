package fr.blueslime.roguecraft.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class RCBlockPlaceEvent implements Listener
{
    @EventHandler
    public void event(BlockPlaceEvent event)
    {
        event.setCancelled(true);
    }
}
