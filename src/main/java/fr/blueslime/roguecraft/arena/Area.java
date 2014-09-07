package fr.blueslime.roguecraft.arena;

import java.util.ArrayList;
import org.bukkit.Location;

public class Area
{
    public static enum AreaType { START, NORMAL, BOSS }
    
    private final AreaType areaType;
    private final ArrayList<Location> mobSpawns;
    private final Location spawnLocation; // If spawn Area
    private Door entryDoor;
    private Door exitDoor;
    
    public Area(AreaType areaType, Door entryDoor, Door exitDoor, ArrayList<Location> mobSpawns)
    {
        this.areaType = areaType;
        this.mobSpawns = mobSpawns;
        this.entryDoor = entryDoor;
        this.exitDoor = exitDoor;
        this.spawnLocation = null;
    }
    
    public Area(Door exitDoor, Location spawn)
    {
        this.areaType = AreaType.START;
        this.mobSpawns = null;
        this.entryDoor = null;
        this.exitDoor = exitDoor;
        this.spawnLocation = spawn;
    }
    
    public void enter()
    {
        this.entryDoor.open();
    }
    
    public void entered()
    {
        this.entryDoor.close();
    }
    
    public void exit()
    {
        this.exitDoor.open();
    }
    
    public void exited()
    {
        this.exitDoor.close();
        this.reset();
    }
    
    public void reset()
    {
        this.entryDoor = null;
        this.exitDoor = null;
    }
    
    public Door getEntryDoor()
    {
        return this.entryDoor;
    }
    
    public Door getExitDoor()
    {
        return this.exitDoor;
    }
    
    public ArrayList<Location> getMobSpawns()
    {
        return this.mobSpawns;
    }
    
    public Location getSpawnLocation()
    {
        return this.spawnLocation;
    }
    
    public AreaType getAreaType()
    {
        return this.areaType;
    }
    
    public boolean isStartArea()
    {
        return this.areaType == AreaType.START;
    }
}
