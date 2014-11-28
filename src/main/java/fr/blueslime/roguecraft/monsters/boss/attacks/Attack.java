package fr.blueslime.roguecraft.monsters.boss.attacks;

import fr.blueslime.roguecraft.arena.Arena;
import org.bukkit.entity.Entity;

public abstract class Attack implements Cloneable
{
    public abstract void use(Arena arena, Entity launcher);
    
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    } 
}
