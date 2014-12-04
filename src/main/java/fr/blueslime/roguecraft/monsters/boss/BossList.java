package fr.blueslime.roguecraft.monsters.boss;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum BossList
{
    ZOMBIE(new ZombieBoss()),
    SPIDER(new SpiderBoss()),
    BLAZE(new BlazeBoss()),
    BLUESLIME(new BlueSlimeBoss()),
    AURELIEN(new AurelienBoss());

    private final BasicBoss boss;
    
    private BossList(BasicBoss boss)
    {
        this.boss = boss;
    }
    
    public BasicBoss create()
    {
        try
        {
            return (BasicBoss) this.boss.clone();
        }
        catch (CloneNotSupportedException ex)
        {
            Logger.getLogger(BossList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static BossList randomBoss()
    {
        Random rand = new Random();
        int index = rand.nextInt(BossList.values().length);
        
        return BossList.values()[index];
    }
}
