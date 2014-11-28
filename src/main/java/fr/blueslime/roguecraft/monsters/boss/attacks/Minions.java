package fr.blueslime.roguecraft.monsters.boss.attacks;

import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.monsters.BasicMonster;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;

public class Minions extends Attack
{
    @Override
    public void use(Arena arena, Entity launcher)
    {
        EntityType type = (
            launcher.getType() == EntityType.ZOMBIE ? EntityType.ZOMBIE :
            launcher.getType() == EntityType.SPIDER ? EntityType.CAVE_SPIDER :
            launcher.getType() == EntityType.BLAZE ? EntityType.MAGMA_CUBE :
            launcher.getType() == EntityType.ENDERMAN ? EntityType.SILVERFISH :
            EntityType.ARROW
        );
        
        for(int i = 0; i < 5; i++)
        {
            BasicMonster monster = new BasicMonster(type);
            arena.getWave().registerMob(monster);
            Entity entity = monster.spawnMob(arena, launcher.getLocation(), arena.getWaveCount());
            
            if(type == EntityType.ZOMBIE)
                ((Zombie) entity).setBaby(true);
        }
    }
}