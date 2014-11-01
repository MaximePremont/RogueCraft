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
        if(RogueCraft.getPlugin().getArena().canJoin())
        {
            event.refuse(ChatColor.RED + "L'arène est pleine.");
            return;
        }
        
        String response = RogueCraft.getPlugin().getArena().joinPlayer(event.getPlayer());
        
        if(!response.equals("OK"))
            event.refuse(ChatColor.RED + response);
    }
}
