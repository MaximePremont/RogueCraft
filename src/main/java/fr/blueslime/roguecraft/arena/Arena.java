package fr.blueslime.roguecraft.arena;

import fr.blueslime.roguecraft.Messages;
import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Wave.WaveType;
import fr.blueslime.roguecraft.randomizer.RandomizerBoss;
import fr.blueslime.roguecraft.randomizer.RandomizerChestLoots;
import fr.blueslime.roguecraft.randomizer.RandomizerMonster;
import fr.blueslime.roguecraft.randomizer.RandomizerLogic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import net.samagames.gameapi.GameAPI;
import net.samagames.gameapi.json.Status;
import net.samagames.gameapi.types.GameArena;
import net.zyuiop.MasterBundle.FastJedis;
import net.zyuiop.statsapi.StatsApi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import redis.clients.jedis.ShardedJedis;

public class Arena implements GameArena
{    
    private final UUID arenaID;
    private final WaveSystem waveSystem;
    private final RandomizerLogic randomizerLogic;
    private final RandomizerMonster randomizerMonster;
    private final RandomizerBoss randomizerBoss;
    private final RandomizerChestLoots randomizerChestLoots;
    private final World world;
    private final ArrayList<ArenaPlayer> arenaPlayers;
    private final HashMap<Area, WaveType> areas;
    private final int maxPlayers, minPlayers;
    private String mapName;
    private BeginTimer timer;
    private Area actualArea;
    private Wave wave;
    private int waveCount;
    private Status status;
        
    public Arena(UUID arenaID, World world) 
    {
        this.arenaID = arenaID;
        this.arenaPlayers = new ArrayList<>();        
        this.waveSystem = new WaveSystem(this);
        this.randomizerLogic = new RandomizerLogic();
        this.randomizerMonster = new RandomizerMonster();
        this.randomizerBoss = new RandomizerBoss();
        this.randomizerChestLoots = new RandomizerChestLoots();
        this.areas = new HashMap<>();
        this.timer = null;
        this.waveCount = 0;
        this.maxPlayers = 5;
        this.minPlayers = 3;
        this.status = Status.Available;
        this.world = world;
        
        for(Entity entity : this.world.getEntities())
        {
            entity.remove();
        }
        
        this.world.setGameRuleValue("doFireTick", "false");
    }
    
    public void joinPlayer(UUID playerID)
    {
        Player player = Bukkit.getPlayer(playerID);
        ArenaPlayer aPlayer = new ArenaPlayer(this, player);

        player.sendMessage(Messages.joinArena);
        player.teleport(new Location(this.world, 0, 70, 0));
        this.arenaPlayers.add(aPlayer);

        this.broadcastMessage(Messages.playerJoinedArena.replace("${PSEUDO}", player.getName()).replace("${JOUEURS}", "" + this.arenaPlayers.size()).replace("${JOUEURS_MAX}", "" + this.maxPlayers));

        refreshPlayers(true);
        setupPlayer(player);

        for(ArenaPlayer pPlayer : this.arenaPlayers)
        {                    
            if(pPlayer.getPlayer().getPlayer() != null)
            {
                pPlayer.getPlayer().getPlayer().getPlayer().getPlayer().hidePlayer(player);
                player.hidePlayer(pPlayer.getPlayer().getPlayer().getPlayer().getPlayer());
            }
        }

        ArrayList<ArenaPlayer> removal = new ArrayList<>();

        for(ArenaPlayer pPlayer : this.arenaPlayers)
        {            
            if(pPlayer.getPlayer().getPlayer() == null)
            {
                removal.add(pPlayer);
                continue;
            }

            pPlayer.getPlayer().getPlayer().getPlayer().getPlayer().showPlayer(player);
            player.showPlayer(pPlayer.getPlayer().getPlayer().getPlayer().getPlayer());
        }

        for (ArenaPlayer pPlayer : removal)
        {
            this.arenaPlayers.remove(pPlayer);
        }

        player.getInventory().clear();
        player.getInventory().setItem(8, RogueCraft.getPlugin().getLeaveItem());
        player.sendMessage(ChatColor.GOLD + "\nBienvenue sur " + ChatColor.RED + "RogueCraft" + ChatColor.GOLD + " !");

        GameAPI.getManager().refreshArena(this);
    }
    
    public void broadcastMessage(String message)
    {
        for(ArenaPlayer player : this.arenaPlayers)
        {
            if(Bukkit.getPlayer(player.getPlayerID()) != null)
                player.getPlayer().getPlayer().sendMessage(message);
        }
    }
    
    public void broadcastSound(Sound s)
    {
        for(ArenaPlayer player : this.arenaPlayers)
        {
            player.getPlayer().getPlayer().playSound(player.getPlayer().getPlayer().getLocation(), s, 1, 1);
        }
    }
    
    public void broadcastSound(Sound s, Location l)
    {
        for(ArenaPlayer player : this.arenaPlayers)
        {
            player.getPlayer().getPlayer().playSound(l, s, 1, 1);
        }
    }
    
    public void refreshPlayers(boolean addPlayers)
    {        
        if(isGameStarted())
        {
            GameAPI.getManager().refreshArena(this);
            return;
        }
        
        if(timer != null && this.arenaPlayers.size() < this.minPlayers)
        {
            broadcastMessage(Messages.notEnougthPlayers);
            timer.setTimeout(0);
            timer.end();
            timer = null;
            
            status = Status.Available;
            
            GameAPI.getManager().refreshArena(this);
            return;
        }
        
        if (timer == null && this.arenaPlayers.size() >= this.minPlayers) 
        {
            timer = new BeginTimer(this);
            timer.start();
            
            status = Status.Starting;
            
            GameAPI.getManager().refreshArena(this);
        }
    }
    
    public void setupPlayer(Player player)
    {
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20.0D);
        player.setSaturation(20);
        player.getInventory().clear();
        player.setExp(0.0F);
        player.setLevel(0);
        
        for(PotionEffect pe : player.getActivePotionEffects())
            player.removePotionEffect(pe.getType());
    }

    public void startGame()
    {
        this.status = Status.InGame;
        
        ArrayList<ArenaPlayer> remove = new ArrayList<>();
                        
        for(ArenaPlayer player : this.arenaPlayers)
        {
            Player p = player.getPlayer().getPlayer();
            
            if(p == null)
            {
                remove.add(player);
                continue;
            }
                        
            this.setupPlayer(p);
            
            p.setFireTicks(0);
            p.sendMessage(Messages.gameStart);
            
            player.giveStuff();
            
            StatsApi.increaseStat(p, "roguecraft", "played_games", 1);
        }
        
        for (ArenaPlayer pPlayer : remove)
        {
            this.arenaPlayers.remove(pPlayer);
        }
        
        if(this.timer != null)
            this.timer.end();
        
        this.timer = null;
        GameAPI.getManager().refreshArena(this);
                
        broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[CONSEIL] Ce jeu doit se jouer en coopération avec les autres joueurs.");
        broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[CONSEIL] Nous pouvons que vous conseillez de vous réunir en vocal, sur le TeamSpeak de SamaGames par exemple ;)");
        
        this.waveCount = 0;
        this.waveSystem.next();
    }
    
    public void endGame()
    {
        this.status = Status.Stopping;
        GameAPI.getManager().refreshArena(this);
        
        for(ArenaPlayer player : this.arenaPlayers)
        {
            RogueCraft.getPlugin().kickPlayer(player.getPlayer().getPlayer());
        }
        
        this.arenaPlayers.clear();
    }
    
    public void finish()
    {                
        broadcastMessage(ChatColor.GOLD + "===========================================");
        broadcastMessage(" ");
        broadcastMessage(Messages.tryAgainLater);
        broadcastMessage(" ");
        broadcastMessage(ChatColor.GOLD + "===========================================");
        
        broadcastSound(Sound.WITHER_DEATH);
                
        Bukkit.getScheduler().runTaskLater(RogueCraft.getPlugin(), new Runnable()
        {
            @Override
            public void run()
            {
                endGame();
            }
        }, 10*20L);
        
        Bukkit.getScheduler().runTaskLater(RogueCraft.getPlugin(), new Runnable()
        {
            @Override
            public void run()
            {
                Bukkit.shutdown();
            }
        }, 12*20L);
    }

    public void lose(Player player)
    {
        this.getPlayer(player).updateWaveStat();
        
        this.arenaPlayers.remove(this.getPlayer(player));
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        
        if(this.getActualPlayers() != 0)
        {
            broadcastMessage(Messages.eliminatedPlayer.replace("${PSEUDO}", player.getName()).replace("${REMAINPLAYERS}", Integer.toString(this.getActualPlayers())));
        }
        else
        {
            broadcastMessage(Messages.lastEliminatedPlayer.replace("${PSEUDO}", player.getName()));
            this.finish();
        }
    }
    
    public void loseRespawn(Player player)
    {
        player.getInventory().setItem(8, RogueCraft.getPlugin().getLeaveItem());
        loseHider(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
        player.teleport(this.getWave().getWaveArea().getPlayersSpawn());
        player.setAllowFlight(true);
        player.setFlying(true);
    }
    
    public void loseHider(Player player)
    {
        for(ArenaPlayer aPlayer : this.arenaPlayers)
        {
            aPlayer.getPlayer().getPlayer().hidePlayer(player);
        }
    }
    
    public void stumpPlayer(ArenaPlayer aPlayer)
    {        
        if(this.arenaPlayers.contains(aPlayer))
            this.arenaPlayers.remove(aPlayer);
        
        if (!this.isGameStarted())
        {
            this.refreshPlayers(false);
            return;
        }
        
        if (this.getActualPlayers() == 0)
        {
            this.finish();
        }
        else if (this.getArenaPlayers().isEmpty())
        {
            Bukkit.shutdown();
        }
        
        GameAPI.getManager().refreshArena(this);
    }
    
    public void addCoin(Player player, int count)
    {
        for(ArenaPlayer p : this.arenaPlayers)
        {
            if(p.getPlayer().getPlayer().getUniqueId().equals(player.getUniqueId()))
            {
                p.addCoins(count);
            }
        }
    }

    public void registerArea(WaveType type, Area area)
    {
        this.areas.put(area, type);
    }
    
    public void setMapName(String mapName)
    {
        this.mapName = mapName;
    }

    public void setWave(Wave wave)
    {
        this.wave = wave;
    }

    public void setActualArea(Area actualArea)
    {
        this.actualArea = actualArea;
    }
    
    @Override
    public void setStatus(Status status)
    {
        this.status = status;
    }
    
    public void upWaveCount()
    {
        this.waveCount += 1;
    }
    
    @Override
    public int countGamePlayers()
    {
        return this.getActualPlayers();
    }

    public String getMapName()
    {
        return this.mapName;
    }

    public Wave getWave()
    {
        return this.wave;
    }
    
    public int getWaveCount()
    {
        return this.waveCount;
    }
    
    public World getWorld()
    {
        return this.world;
    }

    public Area getActualArea()
    {
        return this.actualArea;
    }

    public ArenaPlayer getPlayer(Player player)
    {
        for(ArenaPlayer aPlayer : this.arenaPlayers)
        {            
            if(aPlayer.getPlayerID().equals(player.getUniqueId()))
                return aPlayer;
        }
        
        return null;
    }
    
    public HashMap<Area, WaveType> getAreas()
    {
        return this.areas;
    }
    
    public ArrayList<Area> getAreasByType(WaveType type)
    {
        ArrayList<Area> temp = new ArrayList<>();
        
        for(Area area : this.areas.keySet())
        {
            if(this.areas.get(area) == type)
                temp.add(area);
        }
        
        return temp;
    }
    
    public WaveSystem getWaveSystem()
    {
        return this.waveSystem;
    }

    public RandomizerLogic getLogicRandomizer()
    {
        return this.randomizerLogic;
    }
    
    public RandomizerMonster getMonsterRandomizer()
    {
        return this.randomizerMonster;
    }
    
    public RandomizerBoss getBossRandomizer()
    {
        return this.randomizerBoss;
    }
    
    public RandomizerChestLoots getChestLootsRandomizer()
    {
        return this.randomizerChestLoots;
    }

    public int getActualPlayers()
    {
        return this.arenaPlayers.size();
    }
    
    public ArrayList<ArenaPlayer> getArenaPlayers()
    {
        return this.arenaPlayers;
    }
    
    public int getMinPlayers()
    {
        return this.minPlayers;
    }
    
    @Override
    public int getMaxPlayers()
    {
        return this.maxPlayers;
    }

    @Override
    public int getTotalMaxPlayers()
    {
        return this.maxPlayers;
    }

    @Override
    public Status getStatus()
    {
        return this.status;
    }
    
    @Override
    public int getVIPSlots()
    {
        return 0;
    }

    @Override
    public UUID getUUID()
    {
        return this.arenaID;
    }

    @Override
    public boolean hasPlayer(UUID uuid)
    {
        for(ArenaPlayer player : this.arenaPlayers)
        {
            if(player.getPlayerID().equals(uuid))
                return true;
        }
        
        return false;
    }
    
    public boolean canJoin()
    {
        return ((status.equals(Status.Available) || status.equals(Status.Starting)) && this.arenaPlayers.size() < this.maxPlayers);
    }
    
    public boolean isGameStarted()
    {
        return (status == Status.InGame);
    }

    @Override
    public boolean isFamous()
    {
        return false;
    }
    
    public boolean hasClass(UUID uuid)
    {
        ShardedJedis redis = FastJedis.jedis();
            
        if(redis.exists("roguecraft:properties:" + uuid))
        {
            return !redis.get("roguecraft:properties:" + uuid).equals("");
        }
        else
        {
            return false;
        }
    }
}
