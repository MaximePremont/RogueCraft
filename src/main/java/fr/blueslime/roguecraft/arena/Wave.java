package fr.blueslime.roguecraft.arena;

import fr.blueslime.roguecraft.monsters.BasicBoss;
import fr.blueslime.roguecraft.monsters.BasicMonster;
import java.util.ArrayList;

public class Wave
{
    public static enum WaveType { NORMAL, BOSS }
    
    private final WaveType waveType;
    private final int waveNumber;
    private final Area waveArea;
    private int monsterCount;
    
    private ArrayList<BasicMonster> monsters;
    private BasicBoss boss;
    
    public Wave(WaveType waveType, int waveNumber, Area waveArea)
    {
        this.waveType = waveType;
        this.waveNumber = waveNumber;
        this.waveArea = waveArea;
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
        this.monsterCount -= 1;
    }
    
    public ArrayList<BasicMonster> getMonsters()
    {
        return this.monsters;
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
