package fr.blueslime.roguecraft.monsters.boss.attacks;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.ArenaPlayer;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class WebMania extends Attack
{
    private HashMap<Location, Block> blocks;
    
    @Override
    public void use(Arena arena, Entity launcher)
    {
        for(ArenaPlayer aPlayer : arena.getArenaPlayers())
        {
            Location l = aPlayer.getPlayer().getLocation();
            
            putBlock(l);
            putBlock(l.add(0.0D, 1.0D, 0.0D));
            
            putBlock(l.add(1.0D, 0.0D, 0.0D));
            putBlock(l.add(1.0D, 1.0D, 0.0D));
            
            putBlock(l.add(0.0D, 0.0D, 1.0D));
            putBlock(l.add(0.0D, 1.0D, 1.0D));
            
            putBlock(l.subtract(1.0D, 0.0D, 0.0D));
            putBlock(l.subtract(1.0D, 0.0D, 0.0D).add(0.0D, 1.0D, 0.0D));
            
            putBlock(l.subtract(0.0D, 0.0D, 1.0D));
            putBlock(l.subtract(0.0D, 0.0D, 1.0D).add(0.0D, 1.0D, 0.0D));
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
        for(Location location : this.blocks.keySet())
        {
            location.getBlock().setType(this.blocks.get(location).getType());
            location.getBlock().setData(this.blocks.get(location).getData());
        }
    }
    
    private void putBlock(Location location)
    {
        if(location.getBlock().getType() != Material.AIR)
        {
            this.blocks.put(location, location.getBlock());
            location.getBlock().setType(Material.WEB);
        }
    }
}
