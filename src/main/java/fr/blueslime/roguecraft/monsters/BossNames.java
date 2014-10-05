package fr.blueslime.roguecraft.monsters;

import java.util.ArrayList;
import java.util.Random;

public class BossNames
{
    private static ArrayList<String> names;
    
    public BossNames()
    {
        names = new ArrayList<>();
    }
    
    public static String randomName()
    {
        Random rand = new Random();
        int index = rand.nextInt(names.size());
        return names.get(index);
    }
}
