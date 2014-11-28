package fr.blueslime.roguecraft.monsters.boss;

import fr.blueslime.roguecraft.monsters.boss.attacks.Minions;
import fr.blueslime.roguecraft.monsters.boss.attacks.Poisonous;
import fr.blueslime.roguecraft.monsters.boss.attacks.TNTRain;
import fr.blueslime.roguecraft.monsters.boss.attacks.WebMania;
import org.bukkit.entity.EntityType;

public class SpiderBoss extends BasicBoss
{
    public SpiderBoss()
    {
        super(EntityType.SPIDER);
        
        this.setCustomName("Mystique");
        
        this.attacks.add(new WebMania());
        this.attacks.add(new TNTRain());
        this.attacks.add(new Minions());
        this.attacks.add(new Poisonous());
    }
}
