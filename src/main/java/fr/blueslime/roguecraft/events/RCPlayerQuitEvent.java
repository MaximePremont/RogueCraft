package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.VirtualPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class RCPlayerQuitEvent implements Listener
{
    @EventHandler
    public void event(PlayerQuitEvent event)
    {
        Arena ar = RogueCraft.getPlugin().getArenasManager().getPlayerArena(event.getPlayer().getUniqueId());
        
        if (ar == null)
        {
            return;
        }
        
        ar.stumpPlayer(new VirtualPlayer(event.getPlayer()));
        RogueCraft.getPlugin().getNetworkManager().sendArenasInfos(false);
    }
}
