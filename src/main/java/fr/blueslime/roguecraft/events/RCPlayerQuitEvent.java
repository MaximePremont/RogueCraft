package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.Arena.Role;
import fr.blueslime.roguecraft.arena.ArenaPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class RCPlayerQuitEvent implements Listener
{
    @EventHandler
    public void event(PlayerQuitEvent event)
    {
        Arena arena = RogueCraft.getPlugin().getArena();
        ArenaPlayer player = arena.getPlayer(event.getPlayer());
        
        if(player.getRole() == Role.PLAYER)
        {
            arena.stumpPlayer(arena.getPlayer(event.getPlayer()));
            arena.getNetworkManager().refreshArena(arena);
        }
    }
}
