package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.ArenaPlayer;
import net.samagames.network.client.GameArena;
import net.samagames.network.client.GamePlayer;
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
        GameArena arena = RogueCraft.getPlugin().getArenasManager().getPlayerArena(player.getUniqueId());
        
        if(!arena.isStarted())
        {
            event.setCancelled(true);
        }
        else
        {
            ArenaPlayer aPlayer = ((Arena)arena).getPlayer(new GamePlayer(player));
            boolean bool = RogueCraft.getPlugin().getStuffManager().dropItem(aPlayer, event.getItemDrop().getItemStack());
            
            if(!bool)
            {
                event.setCancelled(true);
            }
        }
    }
}
