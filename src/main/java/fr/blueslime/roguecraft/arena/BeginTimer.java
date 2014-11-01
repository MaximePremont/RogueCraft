package fr.blueslime.roguecraft.arena;

import fr.blueslime.roguecraft.Messages;
import fr.blueslime.roguecraft.RogueCraft;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

public class BeginTimer extends Thread
{
    private Arena parent;
    private long time;
    private boolean count = false;
    private boolean cont = true;

    public BeginTimer(Arena parent)
    {
        this.parent = parent;
        time = 30; // 30 secondes
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
                formatTime();
                setTimeout((int) time);

                if (parent.getActualPlayers() < parent.getMinPlayers())
                {
                    // Au cas où l'arène ne détecterai pas le manque de joueurs //
                    setTimeout(0);
                    this.end();
                    
                    return;
                }
                else if (time == 0)
                {
                    setTimeout(0);
                    this.end(); // On kill le thread
                    
                    Bukkit.getScheduler().runTask(RogueCraft.getPlugin(), new Runnable()
                    {
                        public void run()
                        {
                            parent.startGame();
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

    public void formatTime()
    {
        int hours = (int) time / 3600;
        int remainder = (int) time - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;
        String time = null;
        
        if (hours > 1)
        {
            if (secs == 0)
            {
                if (mins == 30 || mins == 0)
                {
                    time = hours + "h" + mins;
                }
            }
        }
        else
        {
            if ((mins == 45 || mins == 30 || mins == 20 || mins == 10 || mins == 5 || mins == 3 || mins == 2 || mins == 1) && secs == 0)
            {
                time = mins + " minutes";
            }
            
            if (mins == 1 && secs == 30)
            {
                time = mins + " minutes et " + secs + " secondes";
            }
            
            if (mins == 0)
            {
                if (secs == 30 || secs == 20 || secs == 10 || (secs <= 5 && secs > 0))
                {
                    time = secs + " secondes";
                }
            }
        }
        if (time != null) {
            parent.broadcastMessage(Messages.startIn.replace("${TIME}", time));
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
