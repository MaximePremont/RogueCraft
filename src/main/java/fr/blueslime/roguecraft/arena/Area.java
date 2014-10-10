package fr.blueslime.roguecraft.arena;

import java.util.ArrayList;
import org.bukkit.Location;

public class Area
{
    private final ArrayList<Location> bonusChestSpawns;
    private final ArrayList<Location> mobSpawns;
    private final Location playersSpawn;
    
    public Area(Location playersSpawn, ArrayList<Location> mobSpawns, ArrayList<Location> bonusChestSpawns)
    {
        this.playersSpawn = playersSpawn;
        this.mobSpawns = mobSpawns;
        this.bonusChestSpawns = bonusChestSpawns;
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
