package fr.blueslime.roguecraft.monsters.boss;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public enum BossList
{
    ZOMBIE(new ZombieBoss()),
    SPIDER(new SpiderBoss()),
    BLAZE(new BlazeBoss()),
    BLUESLIME(new BlueSlimeBoss()),
    AURELIEN(new AurelienBoss());

    private final BasicBoss boss;
    
    private BossList(BasicBoss boss)
    {
        this.boss = boss;
    }
    
    public BasicBoss create()
    {
        try
        {
            return (BasicBoss) this.boss.clone();
        }
        catch (CloneNotSupportedException ex)
        {
            Logger.getLogger(BossList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static BossList randomBoss()
    {
        Random rand = new Random();
        int index = rand.nextInt(BossList.values().length);
        
        return BossList.values()[index];
    }
}
