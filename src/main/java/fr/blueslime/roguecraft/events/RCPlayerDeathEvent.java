package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

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
public class RCPlayerDeathEvent implements Listener
{
    @EventHandler
    public void event(PlayerDeathEvent event)
    {
        Player deadPlayer = (Player) event.getEntity();
        Arena arena = RogueCraft.getPlugin().getArena();
        
        event.setDeathMessage(null);
        
        if(arena.isGameStarted())
        {
            if(arena.hasPlayer(deadPlayer.getUniqueId()))
            {
                if(deadPlayer.isDead())
                {
                    event.setKeepLevel(false);
                    event.setDroppedExp(0);

                    for (ItemStack i : deadPlayer.getInventory().getContents())
                    {
                        event.getDrops().remove(i);
                    }

                    for (ItemStack i : deadPlayer.getInventory().getArmorContents())
                    {
                        event.getDrops().remove(i);
                    }

                    event.getDrops().clear();
                    arena.lose(deadPlayer);
                }
            }
        }
    }
}
