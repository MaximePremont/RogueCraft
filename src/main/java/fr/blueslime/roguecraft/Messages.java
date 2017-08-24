package fr.blueslime.roguecraft;

import net.samagames.gameapi.GameAPI;
import org.bukkit.ChatColor;

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
public class Messages
{
    public static final String PLUGIN_TAG = GameAPI.coherenceMachine.getGameTag("RogueCraft");
    
    public static String startWaveIn = PLUGIN_TAG + ChatColor.YELLOW + "Début de la vague dans " + ChatColor.AQUA + "${TIME}";
    public static String eliminatedPlayer = PLUGIN_TAG + "${PSEUDO} à été tué ! (${REMAINPLAYERS} Joueurs restants)";
    public static String lastEliminatedPlayer = PLUGIN_TAG + "${PSEUDO} était notre dernier espoir !";
    public static String tryAgainLater = ChatColor.GREEN + "Vous réessayerez la prochaine fois ;)";
    public static String waveStarting = PLUGIN_TAG + ChatColor.YELLOW + "La vague commencera dans 10 secondes.";
    public static String waveStarted = PLUGIN_TAG + ChatColor.YELLOW + "La vague commence !";
    public static String preparingArea = PLUGIN_TAG + ChatColor.YELLOW + "Sélection de votre prochaine salle...";
    public static String waveEnded = PLUGIN_TAG + ChatColor.YELLOW + "Bravo ! Vous avez résisté à la vague !";
}
