package fr.blueslime.roguecraft.arena;

import fr.blueslime.roguecraft.RogueCraft;
import static java.lang.Thread.sleep;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

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
public class EndWaveTimer extends Thread
{
    private final Arena parent;
    private final boolean count = false;
    private long time;
    private boolean cont = true;

    public EndWaveTimer(Arena parent, long time)
    {
        this.parent = parent;
        this.time = time;
    }

    public void end()
    {
        cont = false;
    }

    public void run()
    {
        while (cont)
        {
            try
            {
                sleep(1000);
                time--;
                setTimeout((int) time);

                if (time == 0)
                {
                    setTimeout(0);
                    this.end(); // On kill le thread
                    
                    Bukkit.getScheduler().runTask(RogueCraft.getPlugin(), new Runnable()
                    {
                        public void run()
                        {
                            parent.getWaveSystem().next();
                        }
                    });
                    return;
                }

            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void setTimeIfLower(int time)
    {
        if (time < this.time)
        {
            this.time = time + 1;
        }
    }

    public void setTimeout(int seconds)
    {
        boolean ring = false;
        
        if (seconds <= 5 && seconds != 0)
        {
            ring = true;
        }
        
        for(ArenaPlayer aPlayer : parent.getArenaPlayers())
        {
            if(aPlayer != null)
            {
                aPlayer.getPlayer().getPlayer().setLevel(seconds);

                if (ring)
                {
                    aPlayer.getPlayer().getPlayer().playSound(aPlayer.getPlayer().getPlayer().getLocation(), Sound.NOTE_PIANO, 1, 1);
                }
                if (seconds == 0)
                {
                    aPlayer.getPlayer().getPlayer().playSound(aPlayer.getPlayer().getPlayer().getLocation(), Sound.NOTE_PLING, 1, 1);
                }
            }
            else
            {
                parent.stumpPlayer(aPlayer);
            }
        }
    }

    public long getTime()
    {
        return this.time;
    }
}
