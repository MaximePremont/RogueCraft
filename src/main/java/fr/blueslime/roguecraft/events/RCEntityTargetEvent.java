package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class RCEntityTargetEvent implements Listener
{
    @EventHandler
    public void event(EntityTargetEvent event)
    {
        if(event.getTarget() instanceof Player)
        {
            event.setCancelled(true);
            
            Player player = (Player) event.getTarget();
            Arena arena = RogueCraft.getPlugin().getArena();

            if(arena.isGameStarted())
            {
                if(arena.hasPlayer(player.getUniqueId()))
                {
                    event.setCancelled(false);
                }
            }
        }
    }
}
