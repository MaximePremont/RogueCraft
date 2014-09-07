package fr.blueslime.roguecraft.arena;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Door
{
    private final ArrayList<Block> doorBlocks;
    
    public Door(ArrayList<Block> doorBlocks)
    {
        this.doorBlocks = doorBlocks;
    }
    
    public void open()
    {
        for(Block block : this.doorBlocks)
        {
            block.setType(Material.AIR);
        }
    }
    
    public void close()
    {
        for(Block block : this.doorBlocks)
        {
            block.getWorld().getBlockAt(block.getLocation()).setType(block.getType());
        }
    }

    public ArrayList<Block> getDoorBlocks()
    {
        return this.doorBlocks;
    }
}
