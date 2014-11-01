package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.Arena.Role;
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
        Player player = event.getPlayer();
        Arena arena = RogueCraft.getPlugin().getArena();
        
        if(!arena.isGameStarted())
        {
            event.setCancelled(true);
        }
        else
        {
            ArenaPlayer aPlayer = arena.getPlayer(player);
            
            if(aPlayer.getRole() == Role.PLAYER)
            {
                boolean bool = RogueCraft.getPlugin().getStuffManager().dropItem(aPlayer, event.getItemDrop().getItemStack());

                if(!bool)
                {
                    event.setCancelled(true);
                }
            }
            else
            {
                event.setCancelled(true);
            }
        }
    }
}
