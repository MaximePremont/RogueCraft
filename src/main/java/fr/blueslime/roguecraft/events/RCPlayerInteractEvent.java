package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

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
                    RogueCraft.getPlugin().kickPlayer(event.getPlayer());
                }
                else if(event.getItem().getType() == Material.BLAZE_ROD)
                {
                    if(event.getItem().getItemMeta() != null && event.getItem().getItemMeta().getDisplayName() != null)
                    {
                        String lore = ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName());
                                        
                        switch (lore)
                        {
                            case "Baguette de force":
                            {
                                Potion potion = new Potion(PotionType.STRENGTH, 1);
                                potion.setSplash(true);
                                ItemStack potionStack = new ItemStack(Material.POTION);
                                potion.apply(potionStack);
                                ThrownPotion trownPotion = (ThrownPotion) event.getPlayer().getLocation().getWorld().spawn(event.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), ThrownPotion.class);
                                trownPotion.setItem(potionStack);
                                break;
                            }

                            case "Baguette de santé":
                            {
                                Potion potion = new Potion(PotionType.INSTANT_HEAL, 1);
                                potion.setSplash(true);
                                ItemStack potionStack = new ItemStack(Material.POTION);
                                potion.apply(potionStack);
                                ThrownPotion trownPotion = (ThrownPotion) event.getPlayer().getLocation().getWorld().spawn(event.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), ThrownPotion.class);
                                trownPotion.setItem(potionStack);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
