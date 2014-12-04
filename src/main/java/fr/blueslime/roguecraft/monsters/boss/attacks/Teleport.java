package fr.blueslime.roguecraft.monsters.boss.attacks;

import fr.blueslime.roguecraft.arena.Arena;
import fr.blueslime.roguecraft.arena.ArenaPlayer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import net.samagames.gameapi.GameUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;

public class Teleport extends Attack
{
    @Override
    public void use(Arena arena, Entity launcher)
    {
        ArrayList<ArenaPlayer> players = new ArrayList<>(arena.getArenaPlayers());
        Collections.shuffle(players, new Random(System.nanoTime()));
        launcher.teleport(players.get(0).getPlayer().getLocation());
        ((Monster) launcher).setTarget(players.get(0).getPlayer());
        
        GameUtils.broadcastSound(Sound.ENDERMAN_TELEPORT);
    }
}
