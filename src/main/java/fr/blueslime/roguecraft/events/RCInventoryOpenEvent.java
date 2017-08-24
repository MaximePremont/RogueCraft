package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.ArenaPlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

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
public class RCInventoryOpenEvent implements Listener
{
    @EventHandler
    public void event(InventoryOpenEvent event)
    {
        Player player = (Player) event.getPlayer();
        Arena arena = RogueCraft.getPlugin().getArena();
        
        if(arena.hasPlayer(event.getPlayer().getUniqueId()))
        {
            ArenaPlayer aPlayer = arena.getPlayer(player);
            
            if(aPlayer.getPlayer().getGameMode() == GameMode.CREATIVE)
            {
                event.setCancelled(true);
            }
            
            if (!event.getInventory().getType().equals(InventoryType.PLAYER) || !event.getInventory().getType().equals(InventoryType.CHEST))
            {
                return;
            }
        }
        else
        {
            event.setCancelled(true);
        }
        
        event.setCancelled(true);
    }
}
