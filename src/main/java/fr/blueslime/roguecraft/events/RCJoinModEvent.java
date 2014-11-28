package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.Messages;
import net.samagames.gameapi.events.JoinModEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RCJoinModEvent implements Listener
{
    @EventHandler
    public void event(JoinModEvent event)
    {
        Bukkit.getPlayer(event.getPlayer()).sendMessage(Messages.PLUGIN_TAG + ChatColor.RED + "Hey! Tu es incognito dans le jeu donc fait gaffe ;) Essai pas de tricher, j'ai tout prévu.");
    }
}
