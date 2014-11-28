package fr.blueslime.roguecraft.arena;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public class BonusChest
{
    private final Location location;
    private final BlockFace face;
    
    public BonusChest(Location location, BlockFace face)
    {
        this.location = location;
        this.face = face;
    }
    
    public Location getLocation()
    {
        return this.location;
    }
    
    public BlockFace getFace()
    {
        return this.face;
    }
}
