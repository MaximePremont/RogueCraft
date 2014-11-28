package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import me.confuser.barapi.BarAPI;
import net.zyuiop.statsapi.StatsApi;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class RCEntityDeathEvent implements Listener
{
    @EventHandler
    public void event(EntityDeathEvent event)
    {
        LivingEntity damaged = (LivingEntity) event.getEntity();
        event.getDrops().clear();
            
        if(damaged.hasMetadata("RC-MOBUUID"))
        {
            Arena arena = RogueCraft.getPlugin().getArena();

            if(damaged.hasMetadata("RC-BOSS"))
            {
                arena.getWave().getBoss().onDeath(event.getEntity().getLocation());
                
                for(Player player : Bukkit.getOnlinePlayers())
                {
                    BarAPI.removeBar(player);
                }
            }

            arena.getWave().monsterKilled();
            
            if(damaged.getKiller() != null)
            {
                StatsApi.increaseStat(damaged.getKiller().getUniqueId(), "roguecraft", "mob_killed", 1);
                arena.getPlayer(damaged.getKiller()).increaseKilledMobsCount();
            }
        }
    }
}
