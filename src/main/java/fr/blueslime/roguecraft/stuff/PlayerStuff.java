package fr.blueslime.roguecraft.stuff;

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
public class PlayerStuff
{
    private int[] helmet;
    private int[] chestplate;
    private int[] leggings;
    private int[] boots;
    
    private int[] bow = null;
    private int[] sword = null;
    private int[] wand = null;
    
    private int[] healPotion;
    private int[] strenthPotion;
    private int steak;
    
    private boolean bedrockPotion;

    public int[] getHelmet()
    {
        return helmet;
    }

    public void setHelmet(int[] helmet)
    {
        this.helmet = helmet;
    }

    public int[] getChestplate()
    {
        return chestplate;
    }

    public void setChestplate(int[] chestplate)
    {
        this.chestplate = chestplate;
    }

    public int[] getLeggings()
    {
        return leggings;
    }

    public void setLeggings(int[] leggings)
    {
        this.leggings = leggings;
    }

    public int[] getBoots()
    {
        return boots;
    }

    public void setBoots(int[] boots)
    {
        this.boots = boots;
    }

    public int[] getBow()
    {
        return bow;
    }

    public void setBow(int[] bow)
    {
        this.bow = bow;
    }

    public int[] getSword()
    {
        return sword;
    }

    public void setSword(int[] sword)
    {
        this.sword = sword;
    }

    public int[] getWand()
    {
        return wand;
    }

    public void setWand(int[] wand)
    {
        this.wand = wand;
    }

    public int[] getHealPotion()
    {
        return healPotion;
    }

    public void setHealPotion(int[] healPotion)
    {
        this.healPotion = healPotion;
    }

    public int[] getStrenthPotion()
    {
        return strenthPotion;
    }

    public void setStrenthPotion(int[] strenthPotion)
    {
        this.strenthPotion = strenthPotion;
    }

    public boolean hasBedrockPotion()
    {
        return bedrockPotion;
    }

    public void setBedrockPotion(boolean bedrockPotion)
    {
        this.bedrockPotion = bedrockPotion;
    }

    public int getSteak()
    {
        return steak;
    }

    public void setSteak(int steak)
    {
        this.steak = steak;
    }
}
