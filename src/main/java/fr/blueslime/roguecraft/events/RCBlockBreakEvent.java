package fr.blueslime.roguecraft.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class RCBlockBreakEvent implements Listener
{
    @EventHandler
    public void event(BlockBreakEvent event)
    {
        event.setCancelled(true);
    }
}
