package fr.blueslime.roguecraft.monsters;

import java.util.ArrayList;
import org.bukkit.entity.*;

public class MonsterManager
{
    private final ArrayList<BasicMonster> monsters;
    private final ArrayList<BasicBoss> bosses;
    
    public MonsterManager()
    {
        this.monsters = new ArrayList<>();
        this.bosses = new ArrayList<>();
        
        this.monsters.add(new TestMob("basic", "Clone of Cece35b", 20.0D, 1.0D, EntityType.ZOMBIE));
        this.bosses.add(new TestBoss("basic", "Clone of Cece35b - Boss", 100.0D, 5.0D, EntityType.ZOMBIE));
    }
    
    public BasicMonster getMonster(String name)
    {
        for(BasicMonster monster : this.monsters)
        {
            if(monster.getRegisteredName().equals(name))
            {
                try
                {
                    return (BasicMonster) monster.clone();
                }
                catch (CloneNotSupportedException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        
        return getMonster("basic");
    }
    
    public BasicBoss getBoss(String name)
    {
        for(BasicBoss boss : this.bosses)
        {
            if(boss.getRegisteredName().equals(name))
            {
                try
                {
                    return (BasicBoss) boss.clone();
                }
                catch (CloneNotSupportedException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        
        return getBoss("basic");
    }
}
