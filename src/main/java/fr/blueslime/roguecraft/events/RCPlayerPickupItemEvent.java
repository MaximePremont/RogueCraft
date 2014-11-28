package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class RCPlayerPickupItemEvent implements Listener
{
    @EventHandler
    public void event(PlayerPickupItemEvent event)
    {
        event.setCancelled(true);
        
        Player player = event.getPlayer();
        Arena arena = RogueCraft.getPlugin().getArena();
        
        if(arena.hasPlayer(player.getUniqueId()))
        {
            event.setCancelled(false);
        }
    }
}
