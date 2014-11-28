package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.ArenaPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class RCPlayerDropItemEvent implements Listener
{
    @EventHandler
    public void event(PlayerDropItemEvent event)
    {
        event.setCancelled(true);
        
        Player player = event.getPlayer();
        Arena arena = RogueCraft.getPlugin().getArena();
        
        if(!arena.isGameStarted())
        {
            event.setCancelled(true);
        }
        else
        {            
            if(arena.hasPlayer(player.getUniqueId()))
            {
                ArenaPlayer aPlayer = arena.getPlayer(player);
                boolean bool = RogueCraft.getPlugin().getStuffManager().dropItem(aPlayer, event.getItemDrop().getItemStack());

                if(bool)
                {
                    event.setCancelled(false);
                }
            }
        }
    }
}
