package fr.blueslime.roguecraft.monsters.boss.attacks;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.ArenaPlayer;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Pumpkinator extends Attack
{
    private HashMap<Player, ItemStack> helmets;
    
    @Override
    public void use(Arena arena, Entity launcher)
    {
        for(ArenaPlayer aPlayer : arena.getArenaPlayers())
        {
            this.helmets.put(aPlayer.getPlayer(), aPlayer.getPlayer().getInventory().getHelmet());
            aPlayer.getPlayer().getInventory().setHelmet(new ItemStack(Material.PUMPKIN, 1));
        }
        
        Bukkit.getScheduler().scheduleSyncDelayedTask(RogueCraft.getPlugin(), new Runnable()
        {
            @Override
            public void run()
            {
                restore();
            }
        }, 20L * 5);
    }

    private void restore()
    {
        for(Player player : this.helmets.keySet())
        {
            player.getInventory().setHelmet(this.helmets.get(player));
        }
    }
}
