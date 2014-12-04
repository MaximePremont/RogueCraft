package fr.blueslime.roguecraft.events;

import net.samagames.gameapi.GameAPI;
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
            if(event.getItem() != null)
            {
                if(event.getItem().getType() == Material.WOOD_DOOR)
                {
                    GameAPI.kickPlayer(event.getPlayer());
                }
            }
            
            if(event.getClickedBlock() != null)
            {
                if(event.getClickedBlock().getType() == Material.WOODEN_DOOR || event.getClickedBlock().getType() == Material.FENCE_GATE || event.getClickedBlock().getType() == Material.TRAP_DOOR)
                {
                    event.setCancelled(true);
                }
            }
        }
    }
}
