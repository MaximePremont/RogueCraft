package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.ArenaPlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class RCInventoryOpenEvent implements Listener
{
    @EventHandler
    public void event(InventoryOpenEvent event)
    {
        Player player = (Player) event.getPlayer();
        Arena arena = RogueCraft.getPlugin().getArena();
        
        if(arena.hasPlayer(event.getPlayer().getUniqueId()))
        {
            ArenaPlayer aPlayer = arena.getPlayer(player);
            
            if(aPlayer.getPlayer().getGameMode() == GameMode.CREATIVE)
            {
                event.setCancelled(true);
            }
            
            if (!event.getInventory().getType().equals(InventoryType.PLAYER) || !event.getInventory().getType().equals(InventoryType.CHEST))
            {
                return;
            }
        }
        else
        {
            event.setCancelled(true);
        }
        
        event.setCancelled(true);
    }
}
