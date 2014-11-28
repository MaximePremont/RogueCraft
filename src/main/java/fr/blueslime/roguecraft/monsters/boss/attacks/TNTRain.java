package fr.blueslime.roguecraft.monsters.boss.attacks;

import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.ArenaPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class TNTRain extends Attack
{
    @Override
    public void use(Arena arena, Entity launcher)
    {
        for(ArenaPlayer aPlayer : arena.getArenaPlayers())
        {
            Player p = aPlayer.getPlayer();
            Location l = p.getLocation();
            
            l.getWorld().spawnEntity(l.add(0.0D, 10.0D, 0.0D), EntityType.PRIMED_TNT);
        }
    }
}
