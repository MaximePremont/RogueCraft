package fr.blueslime.roguecraft.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class RCFoodLevelChangeEvent implements Listener
{
    @EventHandler
    public void event(FoodLevelChangeEvent event)
    {
        event.setCancelled(true);
    }
}
