package fr.blueslime.roguecraft.monsters.boss.attacks;

import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.monsters.BasicMonster;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;

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
public class Minions extends Attack
{
    @Override
    public void use(Arena arena, Entity launcher)
    {
        EntityType type = (
            launcher.getType() == EntityType.ZOMBIE ? EntityType.ZOMBIE :
            launcher.getType() == EntityType.SPIDER ? EntityType.CAVE_SPIDER :
            launcher.getType() == EntityType.BLAZE ? EntityType.MAGMA_CUBE :
            launcher.getType() == EntityType.ENDERMAN ? EntityType.SILVERFISH :
            EntityType.ARROW
        );
        
        for(int i = 0; i < 5; i++)
        {
            BasicMonster monster = new BasicMonster(type);
            arena.getWave().registerMob(monster);
            Entity entity = monster.spawnMob(arena, launcher.getLocation(), arena.getWaveCount());
            
            if(type == EntityType.ZOMBIE)
                ((Zombie) entity).setBaby(true);
        }
    }
}