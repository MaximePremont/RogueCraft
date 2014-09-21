package fr.blueslime.roguecraft.arena;

import fr.blueslime.roguecraft.Messages;
import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.network.Status;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
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

public class Arena
{
    public static enum Role { PLAYER, SPECTATOR }
    
    private String arenaName;
    private String mapName;
    private int maxPlayers;
    private int minPlayers;
    private UUID arenaId;
    private Status status;
    private BeginTimer timer;
    private WaveSystem waveSystem;
    private ArrayList<Area> areas;
    private Area actualArea;
    private int wave;
    private World world;
    private final ArrayList<ArenaPlayer> players;
    
    private File dataSource;
    
    public Arena(World world)
    {
        this.players = new ArrayList<>();        
        this.status = Status.Available;
        this.waveSystem = new WaveSystem(this);
        this.timer = null;
        this.wave = 1;
        
        this.world = world;
        this.world.setGameRuleValue("mobGriefing", "false");
    }
    
    public String addPlayer(Player player)
    {
        if(isPlaying(player.getUniqueId()))
        {
            return Messages.alreadyInArena;
        }
        
        if(!canJoin())
        {
            return Messages.arenaFull;
        }
        
        if(RogueCraft.getPlugin().getArenasManager().getPlayerArena(player.getUniqueId()) != null)
        {
            return Messages.alreadyInGame;
        }
        
        player.sendMessage(Messages.joinArena);
        player.teleport(new Location(this.world, 0, 70, 0));
        players.add(new ArenaPlayer(new VirtualPlayer(player)));
        
        this.broadcastMessage(Messages.playerJoinedArena.replace("${PSEUDO}", player.getName()).replace("${JOUEURS}", "" + players.size()).replace("${JOUEURS_MAX}", "" + this.maxPlayers));
    
        refreshPlayers(true);
        setupPlayer(player);
        
        for(Arena arena : RogueCraft.getPlugin().getArenasManager().getArenas().values())
        {
            if(!arena.getArenaName().equals(this.arenaName))
            {
                for(ArenaPlayer pPlayer : this.players)
                {                    
                    if(pPlayer.getPlayer().getPlayer() != null)
                    {
                        pPlayer.getPlayer().getPlayer().getPlayer().getPlayer().hidePlayer(player);
                        player.hidePlayer(pPlayer.getPlayer().getPlayer().getPlayer().getPlayer());
                    }
                }
            }
        }
        
        ArrayList<ArenaPlayer> removal = new ArrayList<>();
        
        for(ArenaPlayer pPlayer : this.players)
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
            this.players.remove(pPlayer);
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
    
        RogueCraft.getPlugin().getNetworkManager().sendArenasInfos(false);
        
        return "good";
    }
    
    public void broadcastMessage(String message)
    {
        for(ArenaPlayer player : this.players)
        {
            player.getPlayer().getPlayer().sendMessage(Messages.PLUGIN_TAG + message);
        }
    }
    
    public void broadcastSound(Sound s)
    {
        for(ArenaPlayer player : this.players)
        {
            player.getPlayer().getPlayer().playSound(player.getPlayer().getPlayer().getLocation(), s, 1, 1);
        }
    }
    
    public void broadcastSound(Sound s, Location l)
    {
        for(ArenaPlayer player : this.players)
        {
            player.getPlayer().getPlayer().playSound(l, s, 1, 1);
        }
    }
    
    public void refreshPlayers(boolean addPlayers)
    {        
        if(isGameStarted())
        {
            RogueCraft.getPlugin().getNetworkManager().sendArenasInfos(false);
            return;
        }
        
        if(timer != null && players.size() < minPlayers)
        {
            broadcastMessage(Messages.notEnougthPlayers);
            timer.setTimeout(0);
            timer.end();
            timer = null;
            
            status = Status.Available;
            
            RogueCraft.getPlugin().getNetworkManager().sendArenasInfos(false);
            
            return;
        }
        
        if (timer == null && players.size() >= minPlayers) 
        {
            timer = new BeginTimer(this);
            timer.start();
            
            status = Status.Starting;
        }
        
        RogueCraft.getPlugin().getNetworkManager().sendArenasInfos(false);
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
                        
        for(ArenaPlayer player : this.players)
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
            
            /** QUOI METTRE DANS LES STATS ? **/
            //StatsApi.increaseStat(p, "roguecraft", "played_games", 1);
        }
        
        if(this.timer != null)
            this.timer.end();
        
        this.timer = null;
        
        RogueCraft.getPlugin().getNetworkManager().sendArenasInfos(false);
        
        broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[CONSEIL] Ce jeu doit se jouer en coopération avec les autres joueurs.");
        broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[CONSEIL] Nous pouvons que vous conseillez de vous réunir en vocal, sur le mumble de SamaGames par exemple ;)");
        
        this.waveSystem.next();
    }
    
    public void endGame()
    {
        this.status = Status.Stopping;
        
        RogueCraft.getPlugin().getNetworkManager().sendArenasInfos(false);
        
        for(ArenaPlayer player : this.players)
        {
            RogueCraft.getPlugin().kickPlayer(player.getPlayer().getPlayer());
        }
        
        this.players.clear();
        
        RogueCraft.getPlugin().getNetworkManager().sendArenasInfos(false);
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
        for(ArenaPlayer aPlayer : this.players)
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
    
    public void stumpPlayer(VirtualPlayer vPlayer)
    {
        ArenaPlayer player = this.getPlayer(vPlayer);
        
        this.players.remove(player);
        
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
        
        RogueCraft.getPlugin().getNetworkManager().sendArenasInfos(false);
    }
    
    public void addCoin(Player player, int count)
    {
        for(ArenaPlayer p : this.players)
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
        RogueCraft.getPlugin().getNetworkManager().sendArenasInfos(false);
    }

    public void setArenaName(String arenaName)
    {
        this.arenaName = arenaName;
    }
    
    public void setMapName(String mapName)
    {
        this.mapName = mapName;
    }
    
    public void setMinPlayers(int minPlayers)
    {
        this.minPlayers = minPlayers;
    }
    
    public void setMaxPlayers(int maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }
    
    public void setArenaId(UUID arenaId)
    {
        this.arenaId = arenaId;
    }
    
    public void setStatus(Status status)
    {
        this.status = status;
    }
    
    public void setDataSource(File dataSource)
    {
        this.dataSource = dataSource;
    }
    
    public void setAreas(ArrayList<Area> areas)
    {
        this.areas = areas;
    }
    
    public void setRoleOfPlayer(Player player, Role role)
    {
        for(ArenaPlayer aPlayer : this.players)
        {
            if(aPlayer.getPlayer().getPlayer().getUniqueId().equals(player.getUniqueId()))
            {
                aPlayer.setRole(role);
            }
        }
    }

    public String getArenaName()
    {
        return this.arenaName;
    }
    
    public String getMapName()
    {
        return this.mapName;
    }
    
    public int getMinPlayers()
    {
        return this.minPlayers;
    }

    public int getMaxPlayers()
    {
        return this.maxPlayers;
    }
    
    public int getWave()
    {
        return this.wave;
    }

    public UUID getArenaId()
    {
        return this.arenaId;
    }
    
    public Status getStatus()
    {
        return this.status;
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
    
    public Area getActualArea()
    {
        return this.actualArea;
    }

    public ArenaPlayer getPlayer(VirtualPlayer vPlayer)
    {
        for(ArenaPlayer player : this.players)
        {            
            if(player.getPlayer().equals(vPlayer))
                return player;
        }
        
        return null;
    }

    public ArrayList<ArenaPlayer> getPlayers()
    {
        return this.players;
    }
    
    public ArrayList<Area> getAreas()
    {
        return this.areas;
    }

    public int getActualPlayers()
    {
        int nb = 0;
        
        for(ArenaPlayer player : this.players)
        {            
            if(player.getRole() == Role.PLAYER)
                nb++;
        }
        
        return nb;
    }
    
    public ArrayList<ArenaPlayer> getActualPlayersList()
    {
        ArrayList<ArenaPlayer> temp = new ArrayList<>();
        
        for(ArenaPlayer player : this.players)
        {
            if(player.getRole() == Role.PLAYER)
                temp.add(player);
        }
        
        return temp;
    }
    
    public boolean canJoin()
    {
        return ((status.equals(Status.Available) || status.equals(Status.Starting)) && players.size() < maxPlayers);
    }
    
    public boolean isGameStarted()
    {
        return (status == Status.InGame);
    }
    
    public boolean isPlaying(VirtualPlayer player)
    {
        return this.getPlayer(player) != null;
    }
    
    public boolean isPlaying(UUID player)
    {
        return isPlaying(new VirtualPlayer(player));
    }
}
