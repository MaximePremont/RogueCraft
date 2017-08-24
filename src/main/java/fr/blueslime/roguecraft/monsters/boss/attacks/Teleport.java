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

/*
 * This file is part of RogueCraft.
 *
 * RogueCraft is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RogueCraft is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with RogueCraft.  If not, see <http://www.gnu.org/licenses/>.
 */
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
