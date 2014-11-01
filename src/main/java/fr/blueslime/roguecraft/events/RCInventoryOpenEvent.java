package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.Arena.Role;
import fr.blueslime.roguecraft.arena.ArenaPlayer;
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
        ArenaPlayer aPlayer = arena.getPlayer(player);
                
        if(aPlayer.getRole() == Role.PLAYER)
        {
            if (!event.getInventory().getType().equals(InventoryType.PLAYER) || !event.getInventory().getType().equals(InventoryType.CHEST))
            {
                return;
            }
        }
        
        event.setCancelled(true);
    }
}
