/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe che rappresenta un oggetto nel gioco
 */
public class GameObject {
    
    /** Nome dell'oggetto */
    private String name;
    
    /** Descrizione dell'oggetto */
    private String description;
    
    /** Indica se l'oggetto può essere raccolto dal giocatore */
    private boolean pickable;
    
    /** Alias con cui l'oggetto può essere chiamato */
    private List<String> aliases;
    
    /**
    * Costruttore principale
    * @param name nome dell'oggetto
    * @param description descrizione dell'oggetto
    * @param pickable se l'oggetto può essere raccolto
    * @param aliases nomi alternativi per riferirsi all'oggetto
    */
    public GameObject(String name, String description, boolean pickable, List<String> aliases) {
        this.name = name;
        this.description = description;
        this.pickable = pickable;
        this.aliases = (aliases != null) ? new ArrayList<>(aliases) : new ArrayList<>();
    }
     
    // ===== Getter e Setter =====


    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean hasName(String name) {
        return this.name.equals(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPickable() {
        return pickable;
    }


    public List<String> getAliases() {
        return aliases;
    }
    
    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, aliases);
    }
    
    /**
     * Stampa la descrizione dell'oggetto.
     */
    public String printDescription() {
        return description;
    }
}
