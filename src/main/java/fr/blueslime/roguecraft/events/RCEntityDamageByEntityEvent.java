package fr.blueslime.roguecraft.events;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.Arena.Role;
import fr.blueslime.roguecraft.arena.ArenaPlayer;
import fr.blueslime.roguecraft.monsters.BasicMonster;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RCEntityDamageByEntityEvent implements Listener
{
    @EventHandler
    public void event(EntityDamageByEntityEvent event)
    {                
        if(event.getEntity().getType() == EntityType.PLAYER)
        {       
            Arena arena = RogueCraft.getPlugin().getArena();

            if(arena.hasPlayer(event.getEntity().getUniqueId()))
            {
                if(event.getDamager().getType() == EntityType.PLAYER)
                {
                    event.setCancelled(true);
                    return;
                }

                event.setDamage(0.0D);

                Player damaged = (Player) event.getEntity();
                double lastDamage;

                if(event.getDamager().getType() != EntityType.PLAYER)
                {
                    Entity damager = event.getDamager();

                    if(damager.hasMetadata("RC-MOBUUID"))
                    {
                        BasicMonster monster = arena.getWave().getMonster(UUID.fromString(damager.getMetadata("RC-MOBUUID").get(0).asString()));

                        if(monster != null)
                        {
                            lastDamage = monster.getCalculatedDamage(event.getDamage(), arena.getWaveCount());
                            damaged.damage(lastDamage);
                        }
                    }
                    else
                    {
                        Bukkit.getLogger().severe("Player damaged by an entity whereas not spawned by the plugin !");
                    }
                }

                if(damaged.isDead())
                {
                    arena.loseMessage(damaged);

                    if(arena.getActualPlayers() == 0)
                    {
                        arena.finish();
                    }
                }
            }
        }
        else
        {
            LivingEntity damaged = (LivingEntity) event.getEntity();
            
            if(damaged.hasMetadata("RC-ARENA"))
            {                
                if(event.getDamager() instanceof Player)
                {
                    Arena arena = RogueCraft.getPlugin().getArena();
                    Player damager = (Player) event.getDamager();
                    
                    if(arena.hasPlayer(event.getEntity().getUniqueId()))
                    {
                        ArenaPlayer player = arena.getPlayer(damager);
                        
                        if(player.getRole() == Role.PLAYER)
                        {
                            if(damager.getItemInHand() != null)
                            {
                                ItemStack stack = damager.getItemInHand();

                                if(stack.getType() == Material.IRON_SWORD)
                                {
                                    if(stack.getItemMeta() != null && stack.getItemMeta().getDisplayName() != null)
                                    {
                                        String lore = ChatColor.stripColor(stack.getItemMeta().getDisplayName());
                                        
                                        switch (lore)
                                        {
                                            case "Epée empoisonnée":
                                                damaged.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 120, 1));
                                                break;

                                            case "Epée de glace":
                                                damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 1));
                                                break;
                                        }
                                    }
                                }
                                else if(stack.getType() == Material.BOW)
                                {
                                    if(stack.getItemMeta() != null && stack.getItemMeta().getDisplayName() != null)
                                    {
                                        String lore = ChatColor.stripColor(stack.getItemMeta().getDisplayName());
                                        
                                        switch (lore)
                                        {
                                            case "Arc empoisonné":
                                                damaged.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 120, 1));
                                                break;

                                            case "Arc de glace":
                                                damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 1));
                                                break;
                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            event.setDamage(0.0D);
                        }
                    }
                }
            }
        }
    }
}
