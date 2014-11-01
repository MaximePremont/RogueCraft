package fr.blueslime.roguecraft.arena;

import fr.blueslime.roguecraft.Messages;
import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Wave.WaveType;
import fr.blueslime.roguecraft.randomizer.RandomizerBoss;
import fr.blueslime.roguecraft.randomizer.RandomizerChestLoots;
import fr.blueslime.roguecraft.randomizer.RandomizerMonster;
import fr.blueslime.roguecraft.randomizer.RandomizerLogic;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import net.samagames.gameapi.GameAPI;
import net.samagames.gameapi.json.Status;
import net.samagames.gameapi.network.NetworkManager;
import net.samagames.gameapi.types.GameArena;
import net.zyuiop.statsapi.StatsApi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Arena implements GameArena
{
    public static enum Role { PLAYER, SPECTATOR }
    
    private final UUID arenaID;
    private final WaveSystem waveSystem;
    private final RandomizerLogic randomizerLogic;
    private final RandomizerMonster randomizerMonster;
    private final RandomizerBoss randomizerBoss;
    private final RandomizerChestLoots randomizerChestLoots;
    private final World world;
    private final ArrayList<ArenaPlayer> arenaPlayers;
    private final HashMap<Area, WaveType> areas;
    private final NetworkManager networkManager;
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
        this.networkManager = GameAPI.getManager();
        this.areas = new HashMap<>();
        this.timer = null;
        this.waveCount = 1;
        this.maxPlayers = 5;
        this.minPlayers = 3;
        
        this.world = world;
        this.world.setGameRuleValue("mobGriefing", "false");
    }
    
    public String joinPlayer(UUID playerID)
    {
        Player player = Bukkit.getPlayer(playerID);
        ArenaPlayer aPlayer = new ArenaPlayer(this, player);
        
        if(aPlayer.hasClass())
        {
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

            player.getInventory().setItem(8, RogueCraft.getPlugin().getLeaveItem());
            player.sendMessage(ChatColor.GOLD + "\nBienvenue sur " + ChatColor.RED + "RogueCraft" + ChatColor.GOLD + " !");
            
            this.networkManager.refreshArena(this);
        }
        else
        {
            return "Vous devez avoir créé une classe pour pouvoir jouer à ce jeu.";
        }
        
        return "OK";
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
            this.networkManager.refreshArena(this);
            return;
        }
        
        if(timer != null && this.arenaPlayers.size() < this.minPlayers)
        {
            broadcastMessage(Messages.notEnougthPlayers);
            timer.setTimeout(0);
            timer.end();
            timer = null;
            
            status = Status.Available;
            
            this.networkManager.refreshArena(this);
            return;
        }
        
        if (timer == null && this.arenaPlayers.size() >= this.minPlayers) 
        {
            timer = new BeginTimer(this);
            timer.start();
            
            status = Status.Starting;
            
            this.networkManager.refreshArena(this);
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
        this.networkManager.refreshArena(this);
                
        broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[CONSEIL] Ce jeu doit se jouer en coopération avec les autres joueurs.");
        broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[CONSEIL] Nous pouvons que vous conseillez de vous réunir en vocal, sur le mumble de SamaGames par exemple ;)");
        
        this.waveCount = 0;
        this.waveSystem.next();
    }
    
    public void endGame()
    {
        this.status = Status.Stopping;
        this.networkManager.refreshArena(this);
        
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
                setupGame();
            }
        }, 12*20L);
    }
    
    public void loseMessage(Player player)
    {        
        if(this.getActualPlayers() != 0)
            broadcastMessage(Messages.eliminatedPlayer.replace("${PSEUDO}", player.getName()).replace("${REMAINPLAYERS}", Integer.toString(this.getActualPlayers())));
        else
            broadcastMessage(Messages.lastEliminatedPlayer.replace("${PSEUDO}", player.getName()));
    }
    
    public void lose(Player player)
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
            if(aPlayer.getRole() != Role.SPECTATOR)
            {
                aPlayer.getPlayer().getPlayer().hidePlayer(player);
            }
            else
            {
                if(player != null && aPlayer.getPlayer().getPlayer() != null)
                {
                    aPlayer.getPlayer().getPlayer().showPlayer(player);
                    player.showPlayer(aPlayer.getPlayer().getPlayer());
                }
            }
        }
    }
    
    public void stumpPlayer(ArenaPlayer aPlayer)
    {        
        this.arenaPlayers.remove(aPlayer);
        
        if (!this.isGameStarted())
        {
            this.refreshPlayers(false);
            return;
        }
        
        if (this.getActualPlayers() == 0)
        {
            finish();
        }
        else if (this.getArenaPlayers().isEmpty())
        {
            endGame();
            setupGame();
        }
        else
        {
            loseMessage(aPlayer.getPlayer());
        }
        
        this.networkManager.refreshArena(this);
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
    
    public void setupGame()
    {
        this.status = Status.Available;
        this.networkManager.refreshArena(this);
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
    
    public void setRoleOfPlayer(Player player, Role role)
    {
        for(ArenaPlayer aPlayer : this.arenaPlayers)
        {
            if(aPlayer.getPlayer().getPlayer().getUniqueId().equals(player.getUniqueId()))
            {
                aPlayer.setRole(role);
            }
        }
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
    
    public long getCount()
    {
        if (timer == null) return 0;
        else return timer.getTime();
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
    
    public NetworkManager getNetworkManager()
    {
        return this.networkManager;
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
        int nb = 0;
        
        for(ArenaPlayer player : this.arenaPlayers)
        {            
            if(player.getRole() == Role.PLAYER)
                nb++;
        }
        
        return nb;
    }
    
    public ArrayList<ArenaPlayer> getActualPlayersList()
    {
        ArrayList<ArenaPlayer> temp = new ArrayList<>();
        
        for(ArenaPlayer player : this.arenaPlayers)
        {
            if(player.getRole() == Role.PLAYER)
                temp.add(player);
        }
        
        return temp;
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
        return ((status.equals(Status.Available) || status.equals(Status.Starting)) && this.arenaPlayers.size() < maxPlayers);
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
}
