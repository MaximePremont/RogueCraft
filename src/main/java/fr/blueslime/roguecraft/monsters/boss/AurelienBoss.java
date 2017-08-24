package fr.blueslime.roguecraft.monsters.boss;

import fr.blueslime.roguecraft.monsters.boss.attacks.Blindinator;
import fr.blueslime.roguecraft.monsters.boss.attacks.Minions;
import fr.blueslime.roguecraft.monsters.boss.attacks.TNTRain;
import fr.blueslime.roguecraft.monsters.boss.attacks.Teleport;
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
public class AurelienBoss extends BasicBoss
{
    public AurelienBoss()
    {
        super(EntityType.ZOMBIE);
        
        this.setCustomName("Fantôme d'Aurelien_Sama");
        this.setCustomHead("Aurelien_Sama");
        
        this.attacks.add(new TNTRain());
        this.attacks.add(new Minions());
        this.attacks.add(new Teleport());
        this.attacks.add(new Blindinator());
    }
}
