package fr.blueslime.roguecraft.arena;

import java.util.ArrayList;
import org.bukkit.Location;

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
public class Area
{
    private final ArrayList<BonusChest> bonusChestSpawns;
    private final ArrayList<Location> mobSpawns;
    private final Location playersSpawn;
    
    public Area(Location playersSpawn, ArrayList<Location> mobSpawns, ArrayList<BonusChest> bonusChestSpawns)
    {
        this.playersSpawn = playersSpawn;
        this.mobSpawns = mobSpawns;
        this.bonusChestSpawns = bonusChestSpawns;
    }
    
    public ArrayList<BonusChest> getBonusChestSpawns()
    {
        return this.bonusChestSpawns;
    }
    
    public ArrayList<Location> getMobSpawns()
    {
        return this.mobSpawns;
    }
    
    public Location getPlayersSpawn()
    {
        return this.playersSpawn;
    }
}
