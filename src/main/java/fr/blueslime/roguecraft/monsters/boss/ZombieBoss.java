package fr.blueslime.roguecraft.monsters.boss;

import fr.blueslime.roguecraft.monsters.boss.attacks.Blindinator;
import fr.blueslime.roguecraft.monsters.boss.attacks.Minions;
import fr.blueslime.roguecraft.monsters.boss.attacks.Pumpkinator;
import fr.blueslime.roguecraft.monsters.boss.attacks.TNTRain;
import org.bukkit.entity.EntityType;

public class ZombieBoss extends BasicBoss
{
    public ZombieBoss()
    {
        super(EntityType.ZOMBIE);
        
        this.setCustomName("Survivant des Abysses");
        
        this.attacks.add(new TNTRain());
        this.attacks.add(new Minions());
        this.attacks.add(new Pumpkinator());
        this.attacks.add(new Blindinator());
    }
}
