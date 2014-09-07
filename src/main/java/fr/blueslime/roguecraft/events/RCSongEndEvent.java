package fr.blueslime.roguecraft.events;

import com.xxmicloxx.NoteBlockAPI.SongEndEvent;
import fr.blueslime.roguecraft.RogueCraft;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RCSongEndEvent implements Listener
{
    @EventHandler
    public void event(SongEndEvent event)
    {
        RogueCraft.getPlugin().getSongMachine().end();
        RogueCraft.getPlugin().getSongMachine().play(event.getSongPlayer().getPlayerList());
    }
}
