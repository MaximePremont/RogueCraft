package fr.blueslime.roguecraft.commands;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStart
{
    public static boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings)
    {
        Arena ar = RogueCraft.getPlugin().getArenasManager().getPlayerArena(((Player) cs).getUniqueId());
        ar.startGame();
        
        return true;
    }
}
