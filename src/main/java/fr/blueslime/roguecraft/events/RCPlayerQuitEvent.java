package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.ArenaPlayer;
import net.samagames.gameapi.GameAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class RCPlayerQuitEvent implements Listener
{
    @EventHandler
    public void event(PlayerQuitEvent event)
    {
        Arena arena = RogueCraft.getPlugin().getArena();
        
        if(arena.hasPlayer(event.getPlayer().getUniqueId()))
        {
            ArenaPlayer player = arena.getPlayer(event.getPlayer());
            arena.lose(player.getPlayer());
            GameAPI.getManager().refreshArena(arena);
        }
    }
}
