/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.util;

import com.google.gson.*;
import it.uniba.lacasadicenere.entity.Item;
import it.uniba.lacasadicenere.entity.ItemContainer;

import java.lang.reflect.Type;

/**
 *
 * @author raffaellanitti
 */
public class ItemDeserializer implements JsonDeserializer<Item> {
    @Override
    public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        
        if (jsonObject.has("containedItems") && jsonObject.get("containedItems").isJsonArray()) {
            return context.deserialize(jsonObject, ItemContainer.class);
        } else {
            return context.deserialize(jsonObject, Item.class);
        }
    }
}
