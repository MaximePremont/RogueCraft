package fr.blueslime.roguecraft.arena;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import fr.blueslime.roguecraft.RogueCraft;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class AreaGenerator
{
    public void next(Arena arena)
    {
        WorldEditPlugin we = RogueCraft.getPlugin().getWorldEditPlugin();
        
        arena.broadcastMessage("Génération de la prochaine salle...");

        arena.broadcastMessage("Génération terminée ! Vous pouvez passer à la prochaine salle :)");
    }

    public Location getCornerOfNextArea(Arena arena)
    {
        Area actual = arena.getActualArea();
        Block baseBlock = actual.getExitDoor().getDoorBlocks().get(4); // Center Block
        
        Location temp = new Location(arena.getWorld(), baseBlock.getX() - 15, baseBlock.getY() - 2, baseBlock.getZ() - 1);
        
        return temp;
    }
}