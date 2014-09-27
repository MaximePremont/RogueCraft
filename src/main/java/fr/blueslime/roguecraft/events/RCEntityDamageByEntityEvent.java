package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.Arena.Role;
import fr.blueslime.roguecraft.arena.VirtualPlayer;
import fr.blueslime.roguecraft.monsters.BasicMonster;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class RCEntityDamageByEntityEvent implements Listener
{
    @EventHandler
    public void event(EntityDamageByEntityEvent event)
    {        
        if(event.getEntity().getType() == EntityType.PLAYER)
        {       
            event.setDamage(0.0D);
            
            Arena arena = RogueCraft.getPlugin().getArenasManager().getPlayerArena(new VirtualPlayer((Player) event.getEntity()));

            if(arena.isGameStarted())
            {
                if(arena.getPlayer(new VirtualPlayer((Player) event.getEntity())).getRole() == Role.PLAYER)
                {
                    Player damaged = (Player) event.getEntity();
                    double lastDamage;
                    
                    if(event.getDamager().getType() != EntityType.PLAYER)
                    {
                        Entity damager = event.getDamager();
                        
                        if(damager.hasMetadata("RC-MOBUUID"))
                        {
                            BasicMonster monster = arena.getWave().getMonster(UUID.fromString(damager.getMetadata("RC-MOBUUID").get(0).asString()));
                            
                            if(monster != null)
                            {
                                lastDamage = monster.getCalculatedDamage(event.getDamage(), arena.getWaveCount());
                                damaged.damage(lastDamage);
                            }
                        }
                        else
                        {
                            Bukkit.getLogger().severe("Player damaged by an entity whereas not spawned by the plugin !");
                        }
                    }
                    
                    if(damaged.isDead())
                    {
                        arena.loseMessage(damaged);
                        
                        if(arena.getActualPlayers() == 0)
                        {
                            arena.finish();
                        }
                    }
                }
            }
        }
        else
        {
            Entity damaged = event.getEntity();
            
            if(damaged.hasMetadata("RC-ARENA"))
            {
                Arena arena = RogueCraft.getPlugin().getArenasManager().getArena(UUID.fromString(damaged.getMetadata("RC-ARENA").get(0).asString()));
                arena.getWave().monsterKilled();
            }
        }
        
        event.setCancelled(true);
    }
}
