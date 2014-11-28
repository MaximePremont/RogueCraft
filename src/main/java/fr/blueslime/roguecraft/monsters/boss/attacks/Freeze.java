package fr.blueslime.roguecraft.monsters.boss.attacks;

import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.ArenaPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Freeze extends Attack
{
    @Override
    public void use(Arena arena, Entity launcher)
    {
        for(ArenaPlayer aPlayer : arena.getArenaPlayers())
        {
            aPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5, 100));
        }
    }
}
