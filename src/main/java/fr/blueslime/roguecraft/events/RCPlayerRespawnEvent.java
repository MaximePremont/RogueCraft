package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RCPlayerRespawnEvent implements Listener
{
    @EventHandler
    public void event(PlayerRespawnEvent event)
    {
        Player player = event.getPlayer();
        Arena arena = RogueCraft.getPlugin().getArena();
        arena.lose(player);
    }
}
