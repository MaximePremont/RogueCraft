package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import net.samagames.network.client.GameArena;
import net.samagames.network.client.GamePlayer;
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
        GameArena arena = RogueCraft.getPlugin().getArenasManager().getPlayerArena(player.getUniqueId());

        if (arena == null)
        {
            return;
        }
        
        event.getRecipients().clear();
        
        for(GamePlayer aPlayer : arena.getPlayers())
        {
            event.getRecipients().add(aPlayer.getPlayer().getPlayer());
        }
    }
}
