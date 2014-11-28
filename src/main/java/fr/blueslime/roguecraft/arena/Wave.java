package fr.blueslime.roguecraft.arena;

import fr.blueslime.roguecraft.monsters.boss.BasicBoss;
import fr.blueslime.roguecraft.monsters.BasicMonster;
import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.Bukkit;

public class Wave
{
    public static enum WaveType { NORMAL, BOSS }
    
    private final Arena arena;
    private final WaveType waveType;
    private final int waveNumber;
    private final Area waveArea;
    private int monsterCount;
    private boolean locked;
    
    private final ArrayList<BasicMonster> monsters;
    private BasicBoss boss;
    
    public Wave(Arena arena, WaveType waveType, int waveNumber, Area waveArea)
    {
        this.arena = arena;
        this.waveType = waveType;
        this.waveNumber = waveNumber;
        this.waveArea = waveArea;
        this.locked = false;
        
        this.monsters = new ArrayList<>();
    }
    
    public void registerMob(BasicMonster monster)
    {
        this.monsters.add(monster);
        this.monsterCount += 1;
    }
    
    public void registerBoss(BasicBoss boss)
    {
        this.boss = boss;
        this.monsterCount += 1;
    }
    
    public void monsterKilled()
    {
        Bukkit.getLogger().info("[RogueCraft] A monster was killed.");
        
        this.monsterCount -= 1;
        
        for(ArenaPlayer player : this.arena.getArenaPlayers())
        {
            player.updateScoreboard();
        }
        
        if(this.monsterCount == 0 && !locked)
        {
            Bukkit.getLogger().info("[RogueCraft] No monster is alive! Ending wave.");
            
            this.locked = true;
            this.arena.getWaveSystem().end();
        }
    }
    
    public ArrayList<BasicMonster> getMonsters()
    {
        return this.monsters;
    }
    
    public BasicMonster getMonster(UUID uuid)
    {
        for(BasicMonster monster : this.monsters)
        {
            if(monster.getUniqueIdentifier().equals(uuid))
                return monster;
        }
        
        return null;
    }
    
    public BasicBoss getBoss()
    {
        return this.boss;
    }
    
    public WaveType getWaveType()
    {
        return this.waveType;
    }
    
    public Area getWaveArea()
    {
        return this.waveArea;
    }
    
    public int getWaveNumber()
    {
        return this.waveNumber;
    }
    
    public int getMonstersLeft()
    {
        return this.monsterCount;
    }
}
