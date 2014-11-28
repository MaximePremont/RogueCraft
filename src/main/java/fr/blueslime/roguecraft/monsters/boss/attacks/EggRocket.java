package fr.blueslime.roguecraft.monsters.boss.attacks;

import fr.blueslime.roguecraft.RogueCraft;
import fr.blueslime.roguecraft.arena.Arena;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;

public class EggRocket extends Attack
{
    @Override
    public void use(Arena arena, Entity launcher)
    {
        for(int i = 0; i < 10; i++)
        {
            Egg egg = (Egg) launcher.getWorld().spawn(((LivingEntity) launcher).getEyeLocation(), Egg.class);
            egg.setMetadata("RC-BOSS-PROJECTILE", new FixedMetadataValue(RogueCraft.getPlugin(), true));
            egg.setVelocity(((LivingEntity) launcher).getEyeLocation().getDirection().multiply(2));
        }
    }
}
