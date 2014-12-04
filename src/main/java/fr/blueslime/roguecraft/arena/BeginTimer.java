package fr.blueslime.roguecraft.arena;

import fr.blueslime.roguecraft.RogueCraft;
import net.samagames.gameapi.GameAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

public class BeginTimer extends Thread
{
    private final Arena parent;
    private final boolean count = false;
    private int time;
    private boolean cont = true;

    public BeginTimer(Arena parent)
    {
        this.parent = parent;
        this.time = 30;
    }

    public void end()
    {
        this.cont = false;
    }

    public void run()
    {
        while (this.cont)
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
        GameAPI.coherenceMachine.getMessageManager().writeStartGameCountdownMessage(this.time);
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
