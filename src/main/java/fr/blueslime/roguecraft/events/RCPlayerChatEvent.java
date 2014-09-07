package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.ArenaPlayer;
import fr.blueslime.roguecraft.arena.VirtualPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class RCPlayerChatEvent implements Listener
{
    @EventHandler
    public void event(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        Arena arena = RogueCraft.getPlugin().getArenasManager().getPlayerArena(new VirtualPlayer(player));

        if (arena == null)
        {
            return;
        }
        
        event.getRecipients().clear();
        
        for(ArenaPlayer aPlayer : arena.getPlayers())
        {
            event.getRecipients().add(aPlayer.getPlayer().getPlayer());
        }
    }
}
