package fr.blueslime.roguecraft.randomizer;

import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.monsters.boss.BasicBoss;
import fr.blueslime.roguecraft.monsters.BasicMonster;
import java.util.ArrayList;

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
public class RandomizerLogic
{
    public ArrayList<BasicMonster> prepareMobs(Arena arena, int mobsCount)
    {
        ArrayList<BasicMonster> monsters = new ArrayList<>();
                        
        for(int i = 0; i < mobsCount; i++)
        {
            monsters.add(arena.getMonsterRandomizer().randomMonster(arena));
        }
        
        return monsters;
    }
    
    public BasicBoss prepareBoss(Arena arena)
    {
        return arena.getBossRandomizer().randomBoss(arena);
    }
}