package fr.blueslime.roguecraft.randomizer;

import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.monsters.BasicBoss;
import fr.blueslime.roguecraft.monsters.BasicMonster;
import java.util.ArrayList;
import java.util.Random;

public class RandomizerLogic
{
    public ArrayList<BasicMonster> prepareMobs(Arena arena)
    {
        ArrayList<BasicMonster> monsters = new ArrayList<>();
        Random rand = new Random();
        
        int mobsCount = arena.getWaveCount() * (rand.nextInt(3) + 1);
                
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