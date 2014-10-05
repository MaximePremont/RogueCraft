package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.Arena.Role;
import fr.blueslime.roguecraft.arena.ArenaPlayer;
import fr.blueslime.roguecraft.monsters.BasicMonster;
import java.util.UUID;
import net.samagames.network.client.GameArena;
import net.samagames.network.client.GamePlayer;
import net.zyuiop.coinsManager.CoinsManager;
import net.zyuiop.statsapi.StatsApi;
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
            
            GameArena arena = RogueCraft.getPlugin().getArenasManager().getPlayerArena(event.getEntity().getUniqueId());

            if(arena.isStarted())
            {
                if(arena.getPlayers().contains(new GamePlayer(event.getEntity().getUniqueId())))
                {
                    Player damaged = (Player) event.getEntity();
                    double lastDamage;
                    
                    if(event.getDamager().getType() != EntityType.PLAYER)
                    {
                        Entity damager = event.getDamager();
                        
                        if(damager.hasMetadata("RC-MOBUUID"))
                        {
                            BasicMonster monster = ((Arena)arena).getWave().getMonster(UUID.fromString(damager.getMetadata("RC-MOBUUID").get(0).asString()));
                            
                            if(monster != null)
                            {
                                lastDamage = monster.getCalculatedDamage(event.getDamage(), ((Arena)arena).getWaveCount());
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
                        ((Arena)arena).loseMessage(damaged);
                        
                        if(((Arena)arena).getActualPlayers() == 0)
                        {
                            ((Arena)arena).finish();
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
                Arena arena = (Arena) RogueCraft.getPlugin().getArenasManager().getArena(UUID.fromString(damaged.getMetadata("RC-ARENA").get(0).asString()));
                arena.getWave().monsterKilled();
                
                for(ArenaPlayer player : arena.getActualPlayersList())
                {
                    if(damaged.hasMetadata("RC-BOSS"))
                    {
                        CoinsManager.creditJoueur(player.getPlayer().getPlayerID(), 100, true);
                        StatsApi.increaseStat(player.getPlayer().getPlayerID(), "roguecraft", "xp", 50);
                    }
                    else
                    {
                        StatsApi.increaseStat(player.getPlayer().getPlayerID(), "roguecraft", "xp", 5);
                    }
                }
            }
        }
        
        event.setCancelled(true);
    }
}
