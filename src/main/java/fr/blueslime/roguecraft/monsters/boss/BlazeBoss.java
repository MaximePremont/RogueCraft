package fr.blueslime.roguecraft.monsters.boss;

import fr.blueslime.roguecraft.monsters.boss.attacks.Blindinator;
import fr.blueslime.roguecraft.monsters.boss.attacks.Brulisator;
import fr.blueslime.roguecraft.monsters.boss.attacks.Minions;
import fr.blueslime.roguecraft.monsters.boss.attacks.TNTRain;
import org.bukkit.entity.EntityType;

public class BlazeBoss extends BasicBoss
{
    public BlazeBoss()
    {
        super(EntityType.BLAZE);
        
        this.setCustomName("Cracheur de flames");
        
        this.attacks.add(new TNTRain());
        this.attacks.add(new Minions());
        this.attacks.add(new Brulisator());
        this.attacks.add(new Blindinator());
    }
}
