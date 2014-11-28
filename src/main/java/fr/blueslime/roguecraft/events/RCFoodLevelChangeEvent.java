package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class RCFoodLevelChangeEvent implements Listener
{
    @EventHandler
    public void event(FoodLevelChangeEvent event)
    {
        event.setCancelled(true);
        
        if(event.getEntity().getType() == EntityType.PLAYER)
        {
            Arena arena = RogueCraft.getPlugin().getArena();

            if(arena.hasPlayer(event.getEntity().getUniqueId()))
            {
                event.setCancelled(false);
            }
        }
    }
}
