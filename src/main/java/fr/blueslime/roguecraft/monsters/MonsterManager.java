package fr.blueslime.roguecraft.monsters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;

public class MonsterManager
{
    private final HashMap<String, BasicMonster> monsters;
    private final HashMap<String, BasicBoss> bosses;
    
    public MonsterManager()
    {
        this.monsters = new HashMap<>();
        this.bosses = new HashMap<>();
        
        registerMob("basic", new TestMob("Clone of Cece35b", 20.0D, 1.0D, EntityType.ZOMBIE));
        registerBoss("basic", new TestBoss("Clone of Cece35b - Boss", 100.0D, 5.0D, EntityType.ZOMBIE));
    }
    
    private void registerMob(String registerName, BasicMonster mob)
    {
        if(this.monsters.containsKey(registerName))
        {
            Bukkit.getLogger().warning("[RogueCraft] Attempting to register a mob while another already has this identifier ! (" + registerName + ")");
            return;
        }
        
        this.monsters.put(registerName, mob);
    }
    
    private void registerBoss(String registerName, BasicBoss mob)
    {
        if(this.bosses.containsKey(registerName))
        {
            Bukkit.getLogger().warning("[RogueCraft] Attempting to register a boss while another already has this identifier ! (" + registerName + ")");
            return;
        }
        
        this.bosses.put(registerName, mob);
    }
    
    public BasicMonster getMonster(String name)
    {
        Iterator<String> keySet = this.monsters.keySet().iterator();
        
        while(keySet.hasNext())
        {
            String s = keySet.next();
            
            if(s.equals(name))
                return this.monsters.get(s);
        }
        
        return getMonster("basic");
    }
    
    public ArrayList<BasicMonster> getMonstersByEntityType(EntityType type)
    {
        ArrayList<BasicMonster> temp = new ArrayList<>();
        
        for(BasicMonster monster : this.monsters.values())
        {
            if(monster.getTypeOfMob() == type)
                temp.add(monster);
        }
        
        return temp;
    }
            
    public HashMap<String, BasicMonster> getMonsters()
    {
        return this.monsters;
    }
    
    public BasicBoss getBoss(String name)
    {
        Iterator<String> keySet = this.bosses.keySet().iterator();
        
        while(keySet.hasNext())
        {
            String s = keySet.next();
            
            if(s.equals(name))
                return this.bosses.get(s);
        }
                
        return getBoss("basic");
    }
    
    public BasicBoss getRandomBoss()
    {
        Random rand = new Random();
        Object[] values = this.bosses.values().toArray();
        return (BasicBoss) values[rand.nextInt(values.length)];
    }
    
    public HashMap<String, BasicBoss> getBosses()
    {
        return this.bosses;
    }
}
