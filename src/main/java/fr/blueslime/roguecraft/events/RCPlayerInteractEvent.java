package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class RCPlayerInteractEvent implements Listener
{
    @EventHandler
    public void event(PlayerInteractEvent event)
    {
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
        {                     
            if (event.getItem() != null && event.getItem().getType().equals(Material.WOOD_DOOR)) 
                RogueCraft.getPlugin().kickPlayer(event.getPlayer());
        }
        else if(event.getAction() == Action.PHYSICAL)
        {
            if(event.getClickedBlock().getType() == Material.IRON_PLATE || event.getClickedBlock().getType() == Material.GOLD_PLATE)
            {
                
            }
        }
        
        event.setCancelled(true);
    }
}
