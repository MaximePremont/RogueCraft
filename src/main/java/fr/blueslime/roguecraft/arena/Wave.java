package fr.blueslime.roguecraft.arena;

public class Wave
{
    public static enum WaveType { NORMAL, BOSS }
    
    private final WaveType waveType;
    private final int waveNumber;
    private int monsters;
    
    public Wave(WaveType waveType, int waveNumber)
    {
        this.waveType = waveType;
        this.waveNumber = waveNumber;
    }
    
    public void monsterKilled()
    {
        this.monsters -= 1;
    }
    
    public WaveType getWaveType()
    {
        return this.waveType;
    }
    
    public int getWaveNumber()
    {
        return this.waveNumber;
    }
    
    public int getMonstersLeft()
    {
        return this.monsters;
    }
}
