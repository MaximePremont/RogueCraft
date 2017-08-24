package fr.blueslime.roguecraft.arena;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

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
public class BonusChest
{
    private final Location location;
    private final BlockFace face;
    
    public BonusChest(Location location, BlockFace face)
    {
        this.location = location;
        this.face = face;
    }
    
    public Location getLocation()
    {
        return this.location;
    }
    
    public BlockFace getFace()
    {
        return this.face;
    }
}
