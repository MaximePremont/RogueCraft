package fr.blueslime.roguecraft;

import org.bukkit.ChatColor;

public class Messages
{
    public static String PLUGIN_TAG = ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "RogueCraft" + ChatColor.DARK_AQUA + "] " + ChatColor.RESET;
    
    public static String notEnougthPlayers = PLUGIN_TAG + ChatColor.RED + "Il n'y a plus assez de joueurs pour commencer.";
    public static String startIn = PLUGIN_TAG + ChatColor.YELLOW + "Début du jeu dans " + ChatColor.AQUA + "${TIME}";
    public static String startWaveIn = PLUGIN_TAG + ChatColor.YELLOW + "Début de la vague dans " + ChatColor.AQUA + "${TIME}";
    public static String alreadyInArena = ChatColor.RED + "Vous êtes dèjà dans l'arène. Ceci est une erreur, merci de nous la signaler.";
    public static String arenaFull = ChatColor.RED + "L'arène est pleine.";
    public static String alreadyInGame = ChatColor.RED + "Vous êtes dèjà en jeu dans une autre arène.";
    public static String joinArena = PLUGIN_TAG + ChatColor.GREEN + "Vous avez rejoint l'arène.";
    public static String playerJoinedArena = PLUGIN_TAG + ChatColor.YELLOW + "${PSEUDO}" + ChatColor.YELLOW + " a rejoint la partie ! " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + "${JOUEURS}" + ChatColor.DARK_GRAY + "/" + ChatColor.RED + "${JOUEURS_MAX}" + ChatColor.DARK_GRAY + "]";
    public static String gameStart = ChatColor.GOLD + "La partie commence !";
    public static String eliminatedPlayer = PLUGIN_TAG + "${PSEUDO} à été tué ! (${REMAINPLAYERS} Joueurs restants)";
    public static String lastEliminatedPlayer = PLUGIN_TAG + "${PSEUDO} était notre dernier espoir !";
    public static String tryAgainLater = PLUGIN_TAG + ChatColor.GREEN + "Vous réessayerez la prochaine fois ;)";
    public static String generatingArea = PLUGIN_TAG + ChatColor.AQUA + "Génération de l'arène, veuillez patienter...";
    public static String waveStarted = PLUGIN_TAG + ChatColor.YELLOW + "La vague commence, attention !";
}
