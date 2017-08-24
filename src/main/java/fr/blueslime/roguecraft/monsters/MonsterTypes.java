package fr.blueslime.roguecraft.monsters;

import java.util.ArrayList;
import org.bukkit.entity.EntityType;

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
public enum MonsterTypes
{
    ZOMBIE(EntityType.ZOMBIE, 1),
    SKELETON(EntityType.SKELETON, 4),
    SPIDER(EntityType.SPIDER, 8),
    PIGMEN(EntityType.PIG_ZOMBIE, 12),
    SLIME(EntityType.SLIME, 15),
    CREEPER(EntityType.CREEPER, 18),
    SILVERFISH(EntityType.SILVERFISH, 23),
    MAGMACUBE(EntityType.MAGMA_CUBE, 28),
    BLAZE(EntityType.BLAZE, 34);
    
    private final EntityType type;
    private final int minWave;

    private MonsterTypes(EntityType type, int minWave)
    {
        this.type = type;
        this.minWave = minWave;
    }
    
    public static ArrayList<EntityType> getMonsterAtWave(int wave)
    {
        ArrayList<EntityType> temp = new ArrayList<>();
        
        for(MonsterTypes type : MonsterTypes.values())
        {
            if(type.getMinWave() <= (wave))
                temp.add(type.getType());
        }
        
        return temp;
    }
    
    private EntityType getType()
    {
        return this.type;
    }
    
    private int getMinWave()
    {
        return this.minWave;
    }
}
