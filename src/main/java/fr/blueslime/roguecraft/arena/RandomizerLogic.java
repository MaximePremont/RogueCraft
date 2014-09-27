package fr.blueslime.roguecraft.arena;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.monsters.BasicBoss;
import fr.blueslime.roguecraft.monsters.BasicMonster;
import fr.blueslime.roguecraft.monsters.MonsterTypes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import org.bukkit.entity.EntityType;

public class RandomizerLogic
{
    public ArrayList<BasicMonster> prepareMobs(Arena arena)
    {
        ArrayList<BasicMonster> monsters = new ArrayList<>();
        Random rand = new Random();
        
        ArrayList<EntityType> typesCanBeUse = MonsterTypes.getMonsterAtWave(arena.getWaveCount());
        ArrayList<BasicMonster> monstersCanBeUse = new ArrayList<>();
        int mobsCount = arena.getWaveCount() * (rand.nextInt(3) + 1);
        
        for(EntityType type : typesCanBeUse)
        {
            monstersCanBeUse.addAll(RogueCraft.getPlugin().getMonsterManager().getMonstersByEntityType(type));
        }
        
        Collections.shuffle(monstersCanBeUse, new Random(System.nanoTime()));
        
        if(monstersCanBeUse.size() < mobsCount)
        {
            while(monstersCanBeUse.size() < mobsCount)
            {
                int index = rand.nextInt(monstersCanBeUse.size());
                monstersCanBeUse.add(monstersCanBeUse.get(index));
            }
        }
        else
        {
            for(int i = 0; i < mobsCount; i++)
            {
                monsters.add(monstersCanBeUse.get(i));
            }
        }
        
        return monsters;
    }
    
    public BasicBoss prepareBoss(Arena arena)
    {
        return RogueCraft.getPlugin().getMonsterManager().getRandomBoss();
    }
}
