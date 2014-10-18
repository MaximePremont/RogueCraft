package fr.blueslime.roguecraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandHelp
{
    public static boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings)
    {
        cs.sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "RogueCraft" + ChatColor.DARK_AQUA + "] " + ChatColor.WHITE + "Aide du plugin");

        addCommandInfo(cs, "help", "Affiche cette aide");
        addCommandInfo(cs, "start", "Lance la partie");

        return true;
    }
    
    private static void addCommandInfo(CommandSender sender, String commandName, String description)
    {
        sender.sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "RogueCraft" + ChatColor.DARK_AQUA + "] " + ChatColor.WHITE + "/rc " + commandName + ": " + description + ChatColor.RESET);
    }
}
