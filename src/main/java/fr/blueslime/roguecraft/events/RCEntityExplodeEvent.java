package fr.blueslime.roguecraft.events;

import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class RCEntityExplodeEvent implements Listener
{
    @EventHandler
    public void event(EntityExplodeEvent event)
    {        
        List<Block> blocks = event.blockList();
        event.blockList().removeAll(blocks);
    }
}
