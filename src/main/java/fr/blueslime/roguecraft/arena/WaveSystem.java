package fr.blueslime.roguecraft.arena;

import com.google.common.collect.Lists;
import com.google.common.math.IntMath;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EmptyClipboardException;
import com.sk89q.worldedit.FilenameException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import fr.blueslime.roguecraft.Messages;
import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Wave.WaveType;
import fr.blueslime.roguecraft.monsters.BasicBoss;
import fr.blueslime.roguecraft.monsters.BasicMonster;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import me.desht.dhutils.TerrainManager;
import net.zyuiop.coinsManager.CoinsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class WaveSystem
{
    private final Arena arena;
    private WaveTimer waveTimer;
    private EndWaveTimer endWaveTimer;
    
    public WaveSystem(Arena arena)
    {
        this.arena = arena;
    }
    
    public void next()
    {
        if(this.endWaveTimer != null)
        {
            this.endWaveTimer.end();
            this.endWaveTimer = null;
        }
        
        for(Player player : Bukkit.getOnlinePlayers())
        {
            player.teleport(new Location(arena.getWorld(), 0.0D, 250.0D, 0.0D));
        }
        
        Bukkit.getLogger().info("[RogueCraft-WaveSystem] Selecting random area...");
        arena.broadcastMessage(Messages.preparingArea);
        
        WaveType waveType = WaveType.NORMAL;
        
        if(arena.getWaveCount() % 10 == 0)
            waveType = WaveType.BOSS;
        
        Bukkit.getLogger().info("[RogueCraft-WaveSystem] Wave type is: " + waveType.name().toUpperCase());
        
        ArrayList<Area> areas = arena.getAreasByType(waveType);
        Collections.shuffle(areas, new Random(System.nanoTime()));
        Area area = areas.get(0);
        
        Wave wave = new Wave(arena, waveType, arena.getWaveCount(), area);
        
        Bukkit.getLogger().info("[RogueCraft-WaveSystem] Creating mob list...");
        
        if(waveType == WaveType.NORMAL)
        {
            ArrayList<BasicMonster> monsters = arena.getLogicRandomizer().prepareMobs(arena);

            for(BasicMonster monster : monsters)
            {
                wave.registerMob(monster);
            }
        }
        else
        {
            BasicBoss boss = arena.getLogicRandomizer().prepareBoss(arena);
            wave.registerBoss(boss);
        }
        
        Bukkit.getLogger().info("[RogueCraft-WaveSystem] Mob list created!");
        Bukkit.getLogger().info("[RogueCraft-WaveSystem] Teleporting players...");
        
        for(Player player : Bukkit.getOnlinePlayers())
        {
            player.teleport(area.getPlayersSpawn().add(0.0D, 1.0D, 1.0D));
        }
        
        Bukkit.getLogger().info("[RogueCraft-WaveSystem] Starting countdown...");
        
        arena.setActualArea(area);
        arena.setWave(wave);
        this.waveTimer = new WaveTimer(arena);
        this.waveTimer.start();
        
        arena.broadcastMessage(Messages.waveStarting);
        
        Bukkit.getLogger().info("[RogueCraft-WaveSystem] Wave generated, end of work! Time to sleep :D");
    }
    
    public void start()
    {
        Wave wave = arena.getWave();
        
        if(this.waveTimer != null)
        {
            this.waveTimer.end();
            this.waveTimer = null;
        }
        
        if(wave.getWaveType() == WaveType.BOSS)
        {
            wave.getBoss().spawnMob(arena, wave.getWaveArea().getMobSpawns().get(0), arena.getWaveCount());
        }
        else
        {
            int partitionSize = IntMath.divide(wave.getMonsters().size(), wave.getWaveArea().getMobSpawns().size(), RoundingMode.UP);
            List<List<BasicMonster>> partitions = Lists.partition(wave.getMonsters(), partitionSize);
            
            for(int i = 0; i < partitions.size(); i++)
            {
                List<BasicMonster> monsterList = partitions.get(i);
                
                for(BasicMonster monster : monsterList)
                {
                    monster.spawnMob(arena, wave.getWaveArea().getMobSpawns().get(i), arena.getWaveCount());
                }
            }
        }
        
        arena.broadcastMessage(Messages.waveStarted);
    }
    
    public void end()
    {
        arena.broadcastMessage(Messages.waveEnded);
        
        for(ArenaPlayer player : arena.getActualPlayersList())
        {
            CoinsManager.creditJoueur(player.getPlayer().getPlayerID(), 10, true);
        }
        
        this.endWaveTimer = new EndWaveTimer(arena);
        this.endWaveTimer.start();
    }
    
    public boolean isFinished()
    {
        return arena.getWave().getMonstersLeft() == 0;
    }
}
