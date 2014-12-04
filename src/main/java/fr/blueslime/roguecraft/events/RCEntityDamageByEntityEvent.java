package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.monsters.BasicMonster;
import java.util.UUID;
import me.confuser.barapi.BarAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
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
            Arena arena = RogueCraft.getPlugin().getArena();

            if(arena.hasPlayer(event.getEntity().getUniqueId()))
            {
                if(event.getDamager().getType() == EntityType.PLAYER)
                {
                    event.setCancelled(true);
                    return;
                }

                event.setDamage(0.0D);

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
            }
            else
            {
                event.setCancelled(true);
            }
        }
        else
        {
            LivingEntity damaged = (LivingEntity) event.getEntity();
            
            if(damaged.hasMetadata("RC-MOBUUID"))
            {                
                if(event.getDamager() instanceof Player)
                {
                    Arena arena = RogueCraft.getPlugin().getArena();
                    Player damager = (Player) event.getDamager();
                    
                    if(!arena.hasPlayer(damager.getUniqueId()))
                    {                        
                        event.setCancelled(true);
                    }
                }
                else if(event.getDamager().getType() == EntityType.CREEPER)
                {
                    event.setCancelled(true);
                }
                
                if(damaged.hasMetadata("RC-BOSS"))
                {
                    LivingEntity entity = (LivingEntity) event.getEntity();
                    
                    for(Player player : Bukkit.getOnlinePlayers())
                    {
                        BarAPI.setHealth(player, ((float) entity.getHealth()) / ((float) entity.getMaxHealth()) * 100);
                    }
                }
            }
        }
    }
}
