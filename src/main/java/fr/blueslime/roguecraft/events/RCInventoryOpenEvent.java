package fr.blueslime.roguecraft.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class RCInventoryOpenEvent implements Listener
{
    @EventHandler
    public void event(InventoryOpenEvent event)
    {
        if (!event.getInventory().getType().equals(InventoryType.PLAYER) || !event.getInventory().getType().equals(InventoryType.CHEST))
        {
            event.setCancelled(false);
        }
    }
}
