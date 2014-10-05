package fr.blueslime.roguecraft.arena;

import fr.blueslime.roguecraft.Messages;
import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.randomizer.RandomizerBoss;
import fr.blueslime.roguecraft.randomizer.RandomizerMonster;
import fr.blueslime.roguecraft.randomizer.RandomizerLogic;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import net.samagames.network.Network;
import net.samagames.network.client.GameArena;
import net.samagames.network.client.GamePlayer;
import net.samagames.network.json.Status;
import net.zyuiop.statsapi.StatsApi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Arena extends GameArena
{
    public static enum Role { PLAYER, SPECTATOR }
    
    private String theme;
    private BeginTimer timer;
    private int minPlayers;
    private WaveSystem waveSystem;
    private RandomizerLogic randomizerLogic;
    private RandomizerMonster randomizerMonster;
    private RandomizerBoss randomizerBoss;
    private ArrayList<Area> areas;
    private Area actualArea;
    private Wave wave;
    private int waveCount;
    private World world;
    private final ArrayList<ArenaPlayer> arenaPlayers;
    
    private File dataSource;
    
    public Arena(World world, int maxPlayers, int maxVIP, String mapName, UUID arenaID)
    {        
        super(maxPlayers, maxVIP, mapName, arenaID, false);
        
        this.arenaPlayers = new ArrayList<>();        
        this.waveSystem = new WaveSystem(this);
        this.randomizerLogic = new RandomizerLogic();
        this.randomizerMonster = new RandomizerMonster();
        this.randomizerBoss = new RandomizerBoss();
        this.timer = null;
        this.waveCount = 1;
        this.maxPlayers = maxPlayers;
        this.mapName = mapName;
        this.arenaID = arenaID;
        
        this.world = world;
        this.world.setGameRuleValue("mobGriefing", "false");
        
        this.setStatus(Status.Available);
    }
    
    @Override
    public String finishJoinPlayer(UUID playerID)
    {
        String ret = super.finishJoinPlayer(playerID);
        Bukkit.getLogger().info(ret);
        
        if (!ret.equals("OK"))
            return ret;
        
        Player player = Bukkit.getPlayer(playerID);
        
        player.sendMessage(Messages.joinArena);
        player.teleport(new Location(this.world, 0, 70, 0));
        this.arenaPlayers.add(new ArenaPlayer(new GamePlayer(player)));
        
        this.broadcastMessage(Messages.playerJoinedArena.replace("${PSEUDO}", player.getName()).replace("${JOUEURS}", "" + players.size()).replace("${JOUEURS_MAX}", "" + this.maxPlayers));
    
        refreshPlayers(true);
        setupPlayer(player);
        
        for(GameArena arena : RogueCraft.getPlugin().getArenasManager().getArenas().values())
        {
            for(ArenaPlayer pPlayer : ((Arena)arena).getArenaPlayers())
            {                    
                if(pPlayer.getPlayer().getPlayer() != null)
                {
                    pPlayer.getPlayer().getPlayer().getPlayer().getPlayer().hidePlayer(player);
                    player.hidePlayer(pPlayer.getPlayer().getPlayer().getPlayer().getPlayer());
                }
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
        
        ItemStack rules = new ItemStack(Material.WRITTEN_BOOK, 1);
        BookMeta rulesMeta = (BookMeta) rules.getItemMeta();
        
        rulesMeta.setAuthor("SamaGames - BlueSlime");
        rulesMeta.setTitle("Règles du jeu");
        
        ArrayList<String> pages = new ArrayList<>();
        pages.add(ChatColor.GOLD + "Bienvenue sur " + ChatColor.DARK_GREEN + "RogueCraft ! \n\n > Sommaire : " + ChatColor.BLACK + "\n\n P.2: Principe du jeu \n P.3: Armes \n\n" + ChatColor.RED + "Pour une meilleure expérience de jeu, désactiver les nuages.\n\n" + ChatColor.BLACK + "Jeu : BlueSlime\nMaps : Amalgar");
        pages.add(ChatColor.DARK_GREEN + "Principe du jeu :" + ChatColor.BLACK + "\n\nSwag");
        pages.add(ChatColor.DARK_GREEN + "Armes :" + ChatColor.BLACK + "\n\nSwag");
    
        rulesMeta.setPages(pages);
        rules.setItemMeta(rulesMeta);
        
        player.getInventory().setItem(0, rules);
        player.sendMessage(ChatColor.GOLD + "\nBienvenue sur " + ChatColor.AQUA + "RogueCraft" + ChatColor.GOLD + " !");
    
        Network.getManager().refreshArena(this);
        
        return "OK";
    }
    
    public void broadcastMessage(String message)
    {
        for(ArenaPlayer player : this.arenaPlayers)
        {
            player.getPlayer().getPlayer().sendMessage(Messages.PLUGIN_TAG + message);
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
            Network.getManager().refreshArena(this);
            return;
        }
        
        if(timer != null && players.size() < this.getMinPlayers())
        {
            broadcastMessage(Messages.notEnougthPlayers);
            timer.setTimeout(0);
            timer.end();
            timer = null;
            
            status = Status.Available;
            
            Network.getManager().refreshArena(this);
            
            return;
        }
        
        if (timer == null && players.size() >= minPlayers) 
        {
            timer = new BeginTimer(this);
            timer.start();
            
            status = Status.Starting;
        }
        
        Network.getManager().refreshArena(this);
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
        
        if(this.timer != null)
            this.timer.end();
        
        this.timer = null;
        
        Network.getManager().refreshArena(this);
        
        broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[CONSEIL] Ce jeu doit se jouer en coopération avec les autres joueurs.");
        broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[CONSEIL] Nous pouvons que vous conseillez de vous réunir en vocal, sur le mumble de SamaGames par exemple ;)");
        
        this.waveSystem.next();
    }
    
    public void endGame()
    {
        this.status = Status.Stopping;
        
        Network.getManager().refreshArena(this);
        
        for(ArenaPlayer player : this.arenaPlayers)
        {
            RogueCraft.getPlugin().kickPlayer(player.getPlayer().getPlayer());
        }
        
        this.arenaPlayers.clear();
        
        Network.getManager().refreshArena(this);
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
    
    public void lose(Player player, Location respawnLocation)
    {
        player.getInventory().setItem(8, RogueCraft.getPlugin().getLeaveItem());
        loseHider(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
        player.teleport(respawnLocation);
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
    
    public void stumpPlayer(GamePlayer vPlayer)
    {
        ArenaPlayer player = this.getPlayer(vPlayer);
        
        this.arenaPlayers.remove(player);
        
        if (!this.isGameStarted())
        {
            this.refreshPlayers(false);
            return;
        }
        
        if (this.getActualPlayers() == 0)
            finish();
        else if (this.getActualPlayers() < 0)
        {
            endGame();
            setupGame();
        }
        
        Network.getManager().refreshArena(this);
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
        Network.getManager().refreshArena(this);
    }
    
    public void setMapName(String mapName)
    {
        this.mapName = mapName;
    }
    
    public void setMinPlayers(int minPlayers)
    {
        this.minPlayers = minPlayers;
    }
    
    public void setTheme(String theme)
    {
        this.theme = theme;
    }

    public void setDataSource(File dataSource)
    {
        this.dataSource = dataSource;
    }
    
    public void setWave(Wave wave)
    {
        this.wave = wave;
    }
    
    public void setAreas(ArrayList<Area> areas)
    {
        this.areas = areas;
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
                GamePlayer gamePlayer = new GamePlayer(player.getUniqueId());
                if(role == Role.SPECTATOR)
                {
                    this.players.remove(gamePlayer);
                    if (!this.spectators.contains(gamePlayer))
                        this.spectators.add(gamePlayer);
                }
                else
                {
                    this.spectators.remove(gamePlayer);
                    if (!this.players.contains(gamePlayer))
                        this.players.add(gamePlayer);
                }
            }
        }
    }
    
    public void upWaveCount()
    {
        this.waveCount += 1;
    }

    public String getTheme()
    {
        return this.theme;
    }

    public Wave getWave()
    {
        return this.wave;
    }
    
    public int getWaveCount()
    {
        return this.waveCount;
    }

    public File getDataSource()
    {
        return this.dataSource;
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
    
    public int getMinPlayers()
    {
        return this.minPlayers;
    }
    
    public Area getActualArea()
    {
        return this.actualArea;
    }

    public ArenaPlayer getPlayer(GamePlayer vPlayer)
    {
        for(ArenaPlayer player : this.arenaPlayers)
        {            
            if(player.getPlayer().equals(vPlayer))
                return player;
        }
        
        return null;
    }
    
    public ArrayList<Area> getAreas()
    {
        return this.areas;
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
    
    @Override
    public int countPlayersIngame()
    {
        return this.arenaPlayers.size();
    }
    
    public boolean canJoin()
    {
        return ((status.equals(Status.Available) || status.equals(Status.Starting)) && players.size() < maxPlayers);
    }
    
    public boolean isGameStarted()
    {
        return (status == Status.InGame);
    }
    
    public boolean isPlaying(GamePlayer player)
    {
        return this.getPlayer(player) != null;
    }
    
    public boolean isPlaying(UUID player)
    {
        return isPlaying(new GamePlayer(player));
    }
}
