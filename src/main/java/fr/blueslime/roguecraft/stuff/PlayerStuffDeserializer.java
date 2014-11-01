package fr.blueslime.roguecraft.stuff;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class PlayerStuffDeserializer implements JsonDeserializer<PlayerStuff>
{
    @Override
    public PlayerStuff deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException
    {
        JsonObject jsonObject = je.getAsJsonObject();
        PlayerStuff playerStuff = new PlayerStuff();
        
        /** ------------------------------------------- **/
        
        JsonArray jsonHelmetValues = jsonObject.get("helmet").getAsJsonArray();
        
        int[] helmetValues = new int[jsonHelmetValues.size()];
        
        for (int i = 0; i < helmetValues.length; i++)
        {
            JsonElement jsonHelmetValue = jsonHelmetValues.get(i);
            helmetValues[i] = jsonHelmetValue.getAsInt();
        }
        
        /** ------------------------------------------- **/
        
        JsonArray jsonChestplateValues = jsonObject.get("chestplate").getAsJsonArray();
        
        int[] chestplateValues = new int[jsonChestplateValues.size()];
        
        for (int i = 0; i < chestplateValues.length; i++)
        {
            JsonElement jsonChestplateValue = jsonChestplateValues.get(i);
            chestplateValues[i] = jsonChestplateValue.getAsInt();
        }
        
        /** ------------------------------------------- **/
        
        JsonArray jsonLeggingsValues = jsonObject.get("leggings").getAsJsonArray();
        
        int[] leggingsValues = new int[jsonLeggingsValues.size()];
        
        for (int i = 0; i < leggingsValues.length; i++)
        {
            JsonElement jsonLeggingsValue = jsonLeggingsValues.get(i);
            leggingsValues[i] = jsonLeggingsValue.getAsInt();
        }
        
        /** ------------------------------------------- **/
        
        JsonArray jsonBootsValues = jsonObject.get("boots").getAsJsonArray();
        
        int[] bootsValues = new int[jsonBootsValues.size()];
        
        for (int i = 0; i < bootsValues.length; i++)
        {
            JsonElement jsonBootsValue = jsonBootsValues.get(i);
            bootsValues[i] = jsonBootsValue.getAsInt();
        }
        
        /** ------------------------------------------- **/
        
        playerStuff.setHelmet(helmetValues);
        playerStuff.setChestplate(chestplateValues);
        playerStuff.setLeggings(leggingsValues);
        playerStuff.setBoots(bootsValues);
        
        /** ------------------------------------------- **/
        
        if(jsonObject.has("bow"))
        {
            JsonArray jsonBowValues = jsonObject.get("bow").getAsJsonArray();
        
            int[] bowValues = new int[jsonBowValues.size()];

            for (int i = 0; i < bowValues.length; i++)
            {
                JsonElement jsonBowValue = jsonBowValues.get(i);
                bowValues[i] = jsonBowValue.getAsInt();
            }
            
            playerStuff.setBow(bowValues);
        }
        else if(jsonObject.has("sword"))
        {
            JsonArray jsonSwordValues = jsonObject.get("sword").getAsJsonArray();
        
            int[] swordValues = new int[jsonSwordValues.size()];

            for (int i = 0; i < swordValues.length; i++)
            {
                JsonElement jsonSwordValue = jsonSwordValues.get(i);
                swordValues[i] = jsonSwordValue.getAsInt();
            }
            
            playerStuff.setSword(swordValues);
        }
        else if(jsonObject.has("wand"))
        {
            JsonArray jsonWandValues = jsonObject.get("wand").getAsJsonArray();
        
            int[] wandValues = new int[jsonWandValues.size()];

            for (int i = 0; i < wandValues.length; i++)
            {
                JsonElement jsonWandValue = jsonWandValues.get(i);
                wandValues[i] = jsonWandValue.getAsInt();
            }
            
            playerStuff.setWand(wandValues);
        }
        
        /** ------------------------------------------- **/
        
        JsonArray jsonHealPotionValues = jsonObject.get("heal-potion").getAsJsonArray();
        
        int[] healPotionValues = new int[jsonHealPotionValues.size()];
        
        for (int i = 0; i < healPotionValues.length; i++)
        {
            JsonElement jsonHealPotionValue = jsonHealPotionValues.get(i);
            healPotionValues[i] = jsonHealPotionValue.getAsInt();
        }
        
        /** ------------------------------------------- **/
        
        JsonArray jsonStrenthPotionValues = jsonObject.get("strenth-potion").getAsJsonArray();
        
        int[] strenthPotionValues = new int[jsonStrenthPotionValues.size()];
        
        for (int i = 0; i < strenthPotionValues.length; i++)
        {
            JsonElement jsonStrenthPotionValue = jsonStrenthPotionValues.get(i);
            strenthPotionValues[i] = jsonStrenthPotionValue.getAsInt();
        }
        
        /** ------------------------------------------- **/
        
        playerStuff.setHealPotion(healPotionValues);
        playerStuff.setStrenthPotion(strenthPotionValues);
        playerStuff.setBedrockPotion(jsonObject.get("bedrock-potion").getAsBoolean());
        playerStuff.setSteak(jsonObject.get("steak").getAsInt());
        
        /** ------------------------------------------- **/
        
        return playerStuff;
    }
}