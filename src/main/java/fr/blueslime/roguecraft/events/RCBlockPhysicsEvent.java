package fr.blueslime.roguecraft.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

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
public class RCBlockPhysicsEvent implements Listener
{
    @EventHandler
    public void event(BlockPhysicsEvent event)
    {
        event.setCancelled(true);
    }
}
