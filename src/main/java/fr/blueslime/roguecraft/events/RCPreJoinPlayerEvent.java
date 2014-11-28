package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import net.samagames.gameapi.events.PreJoinPlayerEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RCPreJoinPlayerEvent implements Listener
{
    @EventHandler
    public void event(PreJoinPlayerEvent event)
    {
        if(RogueCraft.getPlugin().getArena().getArenaPlayers().size() >= RogueCraft.getPlugin().getArena().getTotalMaxPlayers())
        {
            event.refuse(ChatColor.RED + "L'arène est pleine.");
        }
        
        if(!RogueCraft.getPlugin().getArena().hasClass(event.getPlayer()))
        {
            event.refuse(ChatColor.RED + "Vous devez posséder une classe pour pouvoir jouer.");
        }
    }
}
