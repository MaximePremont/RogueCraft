package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import net.samagames.gameapi.events.FinishJoinPlayerEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RCFinishJoinPlayerEvent implements Listener
{
    @EventHandler
    public void event(FinishJoinPlayerEvent event)
    {
        if(RogueCraft.getPlugin().getArena().getArenaPlayers().size() >= RogueCraft.getPlugin().getArena().getTotalMaxPlayers())
        {
            event.refuse(ChatColor.RED + "L'arène est pleine.");
            return;
        }
        
        RogueCraft.getPlugin().getArena().joinPlayer(event.getPlayer());
    }
}
