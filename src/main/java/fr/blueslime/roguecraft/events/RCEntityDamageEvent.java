package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.ArenaPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class RCEntityDamageEvent implements Listener
{
    @EventHandler
    public void event(EntityDamageEvent event)
    {        
        if(event.getEntity() instanceof Player)
        {
            Arena arena = RogueCraft.getPlugin().getArena();
            
            if(arena.hasPlayer(event.getEntity().getUniqueId()))
            {
                ArenaPlayer player = arena.getPlayer((Player) event.getEntity());

                if(player.getRole() == Arena.Role.PLAYER)
                {
                    if(event.getCause() == DamageCause.VOID)
                        player.getPlayer().getPlayer().damage(1000.0D);
                }
                else
                {
                    if(event.getCause() == DamageCause.VOID)
                        player.getPlayer().getPlayer().teleport(arena.getWave().getWaveArea().getPlayersSpawn());
                    else
                        event.setDamage(0.0D);
                }
            }
        }
    }
}
