package fr.blueslime.roguecraft.monsters.boss.attacks;

import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.ArenaPlayer;
import org.bukkit.entity.Entity;

public class Brulisator extends Attack
{
    @Override
    public void use(Arena arena, Entity launcher)
    {
        for(ArenaPlayer aPlayer : arena.getArenaPlayers())
        {
            aPlayer.getPlayer().setFireTicks(100);
        }
    }
}
