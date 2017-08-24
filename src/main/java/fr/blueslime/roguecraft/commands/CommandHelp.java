package fr.blueslime.roguecraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/*
 * This file is part of RogueCraft.
 *
 * RogueCraft is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RogueCraft is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with RogueCraft.  If not, see <http://www.gnu.org/licenses/>.
 */
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
