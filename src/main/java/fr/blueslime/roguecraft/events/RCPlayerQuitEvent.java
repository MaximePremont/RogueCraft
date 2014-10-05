package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import net.samagames.network.Network;
import net.samagames.network.client.GameArena;
import net.samagames.network.client.GamePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class RCPlayerQuitEvent implements Listener
{
    @EventHandler
    public void event(PlayerQuitEvent event)
    {
        GameArena arena = RogueCraft.getPlugin().getArenasManager().getPlayerArena(event.getPlayer().getUniqueId());
        
        if (arena == null)
        {
            return;
        }
        
        ((Arena) arena).stumpPlayer(new GamePlayer(event.getPlayer()));
       Network.getManager().refreshArena(arena);
    }
}
