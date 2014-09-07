package fr.blueslime.roguecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandRogueCraft implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings)
    {
        if(strings[0].equals("help"))
            CommandHelp.onCommand(cs, cmnd, string, strings);
        else if(strings[0].equals("start"))
            CommandStart.onCommand(cs, cmnd, string, strings);
        
        return true;
    }
}
