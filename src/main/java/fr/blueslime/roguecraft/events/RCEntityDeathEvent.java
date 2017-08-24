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

/*
 * This file is part of RogueCraft.
 *
 * RogueCraft is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RogueCraft is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with RogueCraft.  If not, see <http://www.gnu.org/licenses/>.
 */
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
