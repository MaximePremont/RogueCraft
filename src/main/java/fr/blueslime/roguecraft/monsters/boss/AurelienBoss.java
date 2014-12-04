package fr.blueslime.roguecraft.monsters.boss;

import fr.blueslime.roguecraft.monsters.boss.attacks.Blindinator;
import fr.blueslime.roguecraft.monsters.boss.attacks.Minions;
import fr.blueslime.roguecraft.monsters.boss.attacks.TNTRain;
import fr.blueslime.roguecraft.monsters.boss.attacks.Teleport;
import org.bukkit.entity.EntityType;

public class AurelienBoss extends BasicBoss
{
    public AurelienBoss()
    {
        super(EntityType.ZOMBIE);
        
        this.setCustomName("Fantôme d'Aurelien_Sama");
        this.setCustomHead("Aurelien_Sama");
        
        this.attacks.add(new TNTRain());
        this.attacks.add(new Minions());
        this.attacks.add(new Teleport());
        this.attacks.add(new Blindinator());
    }
}
