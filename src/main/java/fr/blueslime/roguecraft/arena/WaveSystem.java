package fr.blueslime.roguecraft.arena;

import fr.blueslime.roguecraft.RogueCraft;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class WaveSystem
{
    private final Arena arena;
    
    public WaveSystem(Arena arena)
    {
        this.arena = arena;
    }
    
    public void next()
    {
        boolean bossWave = false;
        
        if(arena.getWave() % 10 == 0)
            bossWave = true;
        
        File schematicsFolder = new File(RogueCraft.getPlugin().getDataFolder() + "schematics" + File.separator + this.arena.getMapName());
        File[] schematics = schematicsFolder.listFiles();
        
        ArrayList<File> wantedSchematics = new ArrayList<>();
        
        for(File file : schematics)
        {
            if(bossWave && file.getName().startsWith("boss_"))
                wantedSchematics.add(file);
            else if(!bossWave && file.getName().startsWith("normal_"))
                wantedSchematics.add(file);
        }
        
        Collections.shuffle(wantedSchematics, new Random(System.nanoTime()));
        File schematic = wantedSchematics.get(0);
    }
}
