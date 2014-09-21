package fr.blueslime.roguecraft.arena;

import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Area
{    
    private final ArrayList<Block> areaBlocks;
    private ArrayList<Location> bonusChestSpawns;
    private ArrayList<Location> mobSpawns;
    private Location playersSpawn;
    
    public Area(ArrayList<Block> areaBlocks)
    {
        this.areaBlocks = areaBlocks;
        this.bonusChestSpawns = new ArrayList<>();
        this.mobSpawns = new ArrayList<>();
        
        for(Block block : areaBlocks)
        {
            if(block.getType() == Material.EMERALD_BLOCK)
            {
                this.playersSpawn = block.getLocation();
                block.setType(Material.AIR);
            }
            else if(block.getType() == Material.GOLD_BLOCK)
            {
                this.bonusChestSpawns.add(block.getLocation());
                block.setType(Material.AIR);
            }
            else if(block.getType() == Material.DIAMOND_BLOCK)
            {
                this.mobSpawns.add(block.getLocation());
                block.setType(Material.AIR);
            }
        }
    }
    
    public ArrayList<Block> getAreaBlocks()
    {
        return this.areaBlocks;
    }
    
    public ArrayList<Location> getBonusChestSpawns()
    {
        return this.bonusChestSpawns;
    }
    
    public ArrayList<Location> getMobSpawns()
    {
        return this.mobSpawns;
    }
    
    public Location getPlayersSpawn()
    {
        return this.playersSpawn;
    }
}
