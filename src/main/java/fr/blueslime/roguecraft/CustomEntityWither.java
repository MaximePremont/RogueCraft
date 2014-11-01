package fr.blueslime.roguecraft;

import java.lang.reflect.Field;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntityWither;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.World;

public class CustomEntityWither extends EntityWither
{
    public CustomEntityWither(World world)
    {
        super(world);
    }
    
    @Override
    public void e(float sideMot, float forMot)
    {
        this.motY = 0.0D;
        super.e(sideMot, forMot);
    }
    
    @Override
    public void b(NBTTagCompound nbttagcompound) {}

    @Override
    public boolean c(NBTTagCompound nbttagcompound) {
        return false;
    }

    @Override
    public void a(NBTTagCompound nbttagcompound) {}

    @Override
    public boolean d(NBTTagCompound nbttagcompound) {
        return false;
    }

    @Override
    public void e(NBTTagCompound nbttagcompound) {}
}
