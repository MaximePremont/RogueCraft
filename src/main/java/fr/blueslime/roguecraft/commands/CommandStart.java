package fr.blueslime.roguecraft.commands;

import fr.blueslime.roguecraft.RogueCraft;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandStart
{
    public static boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings)
    {
        RogueCraft.getPlugin().getArena().startGame();        
        return true;
    }
}
