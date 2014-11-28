package fr.blueslime.roguecraft.randomizer;

import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.monsters.boss.BasicBoss;
import fr.blueslime.roguecraft.monsters.BasicMonster;
import java.util.ArrayList;

public class RandomizerLogic
{
    public ArrayList<BasicMonster> prepareMobs(Arena arena, int mobsCount)
    {
        ArrayList<BasicMonster> monsters = new ArrayList<>();
                        
        for(int i = 0; i < mobsCount; i++)
        {
            monsters.add(arena.getMonsterRandomizer().randomMonster(arena));
        }
        
        return monsters;
    }
    
    public BasicBoss prepareBoss(Arena arena)
    {
        return arena.getBossRandomizer().randomBoss(arena);
    }
}