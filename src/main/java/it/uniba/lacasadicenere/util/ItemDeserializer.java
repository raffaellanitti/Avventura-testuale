/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.util;

import com.google.gson.*;
import it.uniba.lacasadicenere.entity.Item;
import it.uniba.lacasadicenere.entity.ItemContainer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ItemDeserializer implements JsonDeserializer<Item> {

    @Override
    public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) 
            throws JsonParseException {
        
        JsonObject obj = json.getAsJsonObject();
        
        // Se ha il campo "list", è un ItemContainer
        if (obj.has("list")) {
            String name = obj.get("name").getAsString();
            String description = obj.get("description").getAsString();
            boolean pickable = obj.get("isPickable").getAsBoolean();
            
            List<String> aliases = new ArrayList<>();
            if (obj.has("aliases")) {
                JsonArray aliasArray = obj.getAsJsonArray("aliases");
                for (JsonElement e : aliasArray) {
                    aliases.add(e.getAsString());
                }
            }
            
            ItemContainer container = new ItemContainer(name, description, pickable, aliases);
            
            // Deserializza ricorsivamente gli item contenuti
            if (obj.has("list")) {
                JsonArray listArray = obj.getAsJsonArray("list");
                for (JsonElement e : listArray) {
                    Item contained = context.deserialize(e, Item.class);
                    container.add(contained);
                }
            }
            
            return container;
        }
        
        // Altrimenti è un Item normale
        String name = obj.get("name").getAsString();
        String description = obj.get("description").getAsString();
        boolean pickable = obj.get("isPickable").getAsBoolean();
        
        List<String> aliases = new ArrayList<>();
        if (obj.has("aliases")) {
            JsonArray aliasArray = obj.getAsJsonArray("aliases");
            for (JsonElement e : aliasArray) {
                aliases.add(e.getAsString());
            }
        }
        
        return new Item(name, description, pickable, aliases);
    }
}