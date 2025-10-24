/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta un oggetto presente nel gioco.
 */
public class Item {
    
    /**
     * Nome dell'oggetto
     */
    private String name;

    /**
     * Lista di alias del nome dell'oggetto
     */
    private List<String> aliases;

    /**
     * Descrizione dell'oggetto
     */
    private String description;

    /**
     * Indica se l'oggetto può essere raccolto
     */
    private boolean isPickable;
    
    /**
     * Costruttore dell'oggetto Item.
     * 
     * @param name nome dell'oggetto
     * @param description descrizione dell'oggetto
     * @param pickable indica se l'oggetto può essere raccolto
     * @param aliases eventuali sinonimi del nome
     */
    public Item(String name, String description, boolean pickable, List<String> aliases) {
        this.name = name;
        this.description = description;
        this.isPickable = pickable;
        this.aliases = (aliases != null) ? aliases : new ArrayList<>();
    }
    
    public boolean isPickable() {
        return isPickable;
    }
    
    public void setPickable(boolean pickable) {
        this.isPickable = pickable;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<String> getAliases() {
        return aliases;
    }
    
    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }
    
    /**
     * Verifica se l'oggetto ha un determinato nome o alias.
     * @param name
     * @return
     */
    public boolean hasName(String name) {
        if (this.name.equalsIgnoreCase(name)) {
            return true;
        }
        for (String alias : aliases) {
            if (alias.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
