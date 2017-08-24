package fr.blueslime.roguecraft.utils;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

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
public class ItemUtils
{
    /**
     * Create in a given inventory an item with the given characteristics
     * 
     * @param material ItemStack to display
     * @param quantity Quantity in the stack
     * @param name Display name of the item
     * @param lores Lores of the item (null if none)
     * @param magical Is the item have to be matical
     * @return The displayed item
     */
    public static ItemStack createDisplay(Material material, int quantity, String name, ArrayList<String> lores, boolean magical)
    {
        ItemStack item = new ItemStack(material, quantity);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        
        if(lores != null)
            meta.setLore(lores);
        
        if(magical)
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
        
        item.setItemMeta(meta);
        
        return item;
    }
    
    /**
     * Create in a given inventory an item with the given characteristics
     * 
     * @param material ItemStack to display
     * @param quantity Quantity in the stack
     * @param datatag Damage value of the ItemStack
     * @param name Display name of the item
     * @param lores Lores of the item (null if none)
     * @param magical Is the item have to be matical
     * @return The displayed item
     */
    public static ItemStack createDisplay(Material material, int quantity, int datatag, String name, ArrayList<String> lores, boolean magical)
    {
        ItemStack item = new ItemStack(material, quantity, (short) datatag);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        
        if(lores != null)
            meta.setLore(lores);
        
        if(magical)
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
        
        item.setItemMeta(meta);
        
        return item;
    }
    
    /**
     * Create in a given inventory an item with the given characteristics
     * 
     * @param material ItemStack to display
     * @param inv The inventory
     * @param quantity Quantity in the stack
     * @param slot Slot in the inventory
     * @param name Display name of the item
     * @param lores Lores of the item (null if none)
     * @param magical Is the item have to be matical
     * @return The displayed item
     */
    public static ItemStack createDisplay(Material material, Inventory inv, int quantity, int slot, String name, ArrayList<String> lores, boolean magical)
    {
        ItemStack item = new ItemStack(material, quantity);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);

        if(lores != null)
        {
            ArrayList<String> newList = new ArrayList<>();
            
            for(String lore : lores)
            {
                newList.add(ChatColor.RESET + "" + ChatColor.GRAY + lore);
            }
            
            meta.setLore(newList);
        }
        
        if(magical)
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
        
        item.setItemMeta(meta);
        inv.setItem(slot, item);
        
        return item;
    }
    
    /**
     * Color a leather armor piece with a RGB color
     * 
     * @param item
     * @param red
     * @param green
     * @param blue
     * @return 
     */
    public static ItemStack setColor(ItemStack item, int red, int green, int blue)
    {
        LeatherArmorMeta lam = (LeatherArmorMeta) item.getItemMeta();
        lam.setColor(Color.fromRGB(red, green, blue));
        item.setItemMeta(lam);
        
        return item;
    }
    
    /**
     * Return if a given ItemStack is an armor
     * 
     * @param stack
     * @return 
     */
    public static boolean isArmor(ItemStack stack)
    {
        if(stack.getType() == Material.LEATHER_HELMET || stack.getType() == Material.LEATHER_CHESTPLATE || stack.getType() == Material.LEATHER_LEGGINGS || stack.getType() == Material.LEATHER_BOOTS)
            return true;
        else if(stack.getType() == Material.IRON_HELMET || stack.getType() == Material.IRON_CHESTPLATE || stack.getType() == Material.IRON_LEGGINGS || stack.getType() == Material.IRON_BOOTS)
            return true;
        else if(stack.getType() == Material.CHAINMAIL_HELMET || stack.getType() == Material.CHAINMAIL_CHESTPLATE || stack.getType() == Material.CHAINMAIL_LEGGINGS || stack.getType() == Material.CHAINMAIL_BOOTS)
            return true;
        else if(stack.getType() == Material.GOLD_HELMET || stack.getType() == Material.GOLD_CHESTPLATE || stack.getType() == Material.GOLD_LEGGINGS || stack.getType() == Material.GOLD_BOOTS)
            return true;
        else if(stack.getType() == Material.DIAMOND_HELMET || stack.getType() == Material.DIAMOND_CHESTPLATE || stack.getType() == Material.DIAMOND_LEGGINGS || stack.getType() == Material.DIAMOND_BOOTS)
            return true;
        else
            return false;
    }
    
    /**
     * Return if a given ItemStack is a sword
     * 
     * @param stack
     * @return 
     */
    public static boolean isSword(ItemStack stack)
    {
        return stack.getType() == Material.WOOD_SWORD || stack.getType() == Material.STONE_SWORD || stack.getType() == Material.IRON_SWORD || stack.getType() == Material.GOLD_SWORD || stack.getType() == Material.DIAMOND_SWORD;
    }
}

