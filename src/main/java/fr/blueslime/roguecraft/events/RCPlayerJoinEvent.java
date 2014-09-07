package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.ArenasManager;
import fr.blueslime.roguecraft.arena.VirtualPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class RCPlayerJoinEvent implements Listener
{
    @EventHandler
    public void event(PlayerJoinEvent event)
    {
        final Player p = event.getPlayer();
        final ArenasManager m = RogueCraft.getPlugin().getArenasManager();
        final VirtualPlayer vp = new VirtualPlayer(p.getUniqueId());
        
        if (!m.isAttempted(vp) && !p.isOp())
        {
            event.getPlayer().sendMessage(ChatColor.RED + "Une erreur s'est produite, vous ne pouvez pas joindre l'arène.");
            event.getPlayer().kickPlayer("Impossible de vous connecter.");
            System.out.println("Joueur non attendu.");
            return;
        }
        else if (!m.isAttempted(vp))
        {
            event.getPlayer().sendMessage(ChatColor.GOLD + "Welcome in SANDBOX MODE ! Yay !");
            return;
        }

        event.setJoinMessage(null);

        String res = m.finishJoin(p);
        
        if (!res.equals("good"))
        {
            event.getPlayer().sendMessage(ChatColor.RED + "Une erreur s'est produite, vous ne pouvez pas joindre l'arène.");
            event.getPlayer().sendMessage(ChatColor.RED + "Code erreur : " + res);
            event.getPlayer().kickPlayer("Impossible de vous connecter.");
            System.out.println("Erreur de connexion du joueur : " + res);
        }
    }
}
