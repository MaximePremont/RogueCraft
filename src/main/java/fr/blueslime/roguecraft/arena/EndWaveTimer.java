package fr.blueslime.roguecraft.arena;

import fr.blueslime.roguecraft.RogueCraft;
import static java.lang.Thread.sleep;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

public class EndWaveTimer extends Thread
{
    private Arena parent;
    private long time;
    private boolean count = false;
    private boolean cont = true;

    public EndWaveTimer(Arena parent)
    {
        this.parent = parent;
        time = 5; // 30 secondes
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
    }

    public long getTime()
    {
        return this.time;
    }
}
