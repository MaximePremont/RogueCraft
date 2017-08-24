package fr.blueslime.roguecraft.monsters.boss.attacks;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;

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
public class EggRocket extends Attack
{
    @Override
    public void use(Arena arena, Entity launcher)
    {
        for(int i = 0; i < 10; i++)
        {
            Egg egg = (Egg) launcher.getWorld().spawn(((LivingEntity) launcher).getEyeLocation(), Egg.class);
            egg.setMetadata("RC-BOSS-PROJECTILE", new FixedMetadataValue(RogueCraft.getPlugin(), true));
            egg.setVelocity(((LivingEntity) launcher).getEyeLocation().getDirection().multiply(2));
        }
    }
}
