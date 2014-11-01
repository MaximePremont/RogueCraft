package fr.blueslime.roguecraft.monsters;

import java.util.ArrayList;
import org.bukkit.entity.EntityType;

public enum MonsterTypes
{
    ZOMBIE(EntityType.ZOMBIE, 1),
    SKELETON(EntityType.SKELETON, 4),
    SPIDER(EntityType.SPIDER, 8),
    PIGMEN(EntityType.PIG_ZOMBIE, 12),
    SLIME(EntityType.SLIME, 15),
    CREEPER(EntityType.CREEPER, 18),
    SILVERFISH(EntityType.SILVERFISH, 23),
    MAGMACUBE(EntityType.MAGMA_CUBE, 28),
    BLAZE(EntityType.BLAZE, 34);
    
    private final EntityType type;
    private final int minWave;

    private MonsterTypes(EntityType type, int minWave)
    {
        this.type = type;
        this.minWave = minWave;
    }
    
    public static ArrayList<EntityType> getMonsterAtWave(int wave)
    {
        ArrayList<EntityType> temp = new ArrayList<>();
        
        for(MonsterTypes type : MonsterTypes.values())
        {
            if(type.getMinWave() <= (wave))
                temp.add(type.getType());
        }
        
        return temp;
    }
    
    private EntityType getType()
    {
        return this.type;
    }
    
    private int getMinWave()
    {
        return this.minWave;
    }
}
