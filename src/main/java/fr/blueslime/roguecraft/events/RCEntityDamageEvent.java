package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.ArenaPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

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

                if(event.getCause() == DamageCause.VOID)
                    player.getPlayer().getPlayer().damage(1000.0D);
                if(event.getCause() == DamageCause.FALL)
                    event.setCancelled(true);
            }
            else
            {
                if(event.getCause() == DamageCause.VOID)
                {
                    event.setCancelled(true);
                    ((Player) event.getEntity()).teleport(arena.getWave().getWaveArea().getPlayersSpawn());
                }
                else
                {
                    event.setCancelled(true);
                }
            }
        }
    }
}
