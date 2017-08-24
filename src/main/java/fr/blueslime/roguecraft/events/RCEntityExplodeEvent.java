package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

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
public class RCEntityExplodeEvent implements Listener
{
    @EventHandler
    public void event(EntityExplodeEvent event)
    {        
        List<Block> blocks = event.blockList();
        event.blockList().removeAll(blocks);
        
        if(event.getEntity().hasMetadata("RC-MOBUUID"))
        {
            RogueCraft.getPlugin().getArena().getWave().monsterKilled();
        }
    }
}
