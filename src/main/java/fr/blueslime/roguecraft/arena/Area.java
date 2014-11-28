package fr.blueslime.roguecraft.arena;

import java.util.ArrayList;
import org.bukkit.Location;

public class Area
{
    private final ArrayList<BonusChest> bonusChestSpawns;
    private final ArrayList<Location> mobSpawns;
    private final Location playersSpawn;
    
    public Area(Location playersSpawn, ArrayList<Location> mobSpawns, ArrayList<BonusChest> bonusChestSpawns)
    {
        this.playersSpawn = playersSpawn;
        this.mobSpawns = mobSpawns;
        this.bonusChestSpawns = bonusChestSpawns;
    }
    
    public ArrayList<BonusChest> getBonusChestSpawns()
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
