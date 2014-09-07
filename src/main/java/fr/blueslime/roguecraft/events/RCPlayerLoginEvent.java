package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.ArenasManager;
import fr.blueslime.roguecraft.arena.VirtualPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class RCPlayerLoginEvent implements Listener
{
    @EventHandler
    public void event(PlayerLoginEvent event)
    {
        Player p = event.getPlayer();
        ArenasManager m = RogueCraft.getPlugin().getArenasManager();
        
        if (!m.isAttempted(new VirtualPlayer(p.getUniqueId())) && !p.isOp())
        {
            event.setKickMessage(ChatColor.RED + "Une erreur s'est produite, vous ne pouvez pas joindre l'arène.");
            event.setResult(PlayerLoginEvent.Result.KICK_FULL);
        }
    }
}
