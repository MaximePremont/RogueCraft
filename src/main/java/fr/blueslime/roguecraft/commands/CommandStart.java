package fr.blueslime.roguecraft.commands;

import fr.blueslime.roguecraft.RogueCraft;
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
public class CommandStart
{
    public static boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings)
    {
        RogueCraft.getPlugin().getArena().startGame();        
        return true;
    }
}
