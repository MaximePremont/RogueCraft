package fr.blueslime.roguecraft.arena;

import com.xxmicloxx.NoteBlockAPI.NBSDecoder;
import com.xxmicloxx.NoteBlockAPI.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.Song;
import com.xxmicloxx.NoteBlockAPI.SongPlayer;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SongMachine
{
    private final Plugin plugin;
    private SongPlayer songPlayer;
    private int actual;
    
    public SongMachine(Plugin plugin)
    {
        this.plugin = plugin;
        this.actual = 0;
    }
    
    public void play(List<String> players)
    {
        List<Songs> songs = Collections.unmodifiableList(Arrays.asList(Songs.values()));        
        Song song = NBSDecoder.parse(new File(plugin.getDataFolder(), songs.get(actual).getFileName()));
        
        this.songPlayer = new RadioSongPlayer(song);
        this.songPlayer.setAutoDestroy(true);
        
        for(String string : players)
        {
            this.songPlayer.addPlayer(Bukkit.getPlayer(string));
        }
        
        this.songPlayer.setPlaying(true);
        
        if(this.actual != 3)
            this.actual++;
        else
            this.actual = 0;
    }
    
    public void play(ArrayList<ArenaPlayer> players)
    {
        List<String> temp = new ArrayList<>();
        
        for(ArenaPlayer player : players)
        {
            temp.add(player.getPlayer().getPlayer().getName());
        }
        
        this.play(temp);
    }
    
    public void end()
    {
        if(this.songPlayer != null)
        {
            this.songPlayer.setPlaying(false);
            this.songPlayer.destroy();
        }
    }
    
    public void addPlayer(Player player)
    {
        if(this.songPlayer != null)
        {
            this.songPlayer.addPlayer(player);
        }
    }
    
    public void removePlayer(Player player)
    {
        if(this.songPlayer != null)
        {
            this.songPlayer.removePlayer(player);
        }
    }
}
