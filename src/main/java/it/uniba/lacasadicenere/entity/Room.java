/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.entity;

//import it.uniba.casadicenere.db.DatabaseConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * Classe che rappresenta una stanza del gioco
 * Ogni stanza contiene nome, descrizione e gli oggetti presenti.
 */

public class Room {
    
    /** Nome della stanza */
    private String roomName; 
    
    /** Descrizione della stanza */
    private String roomDescription; 

    /** Lista di oggetti presenti nella stanza */
    private List<GameObject> objects;
    
    /** Indica se la stanza è nascosta */
    private boolean hidden;
    
    /** Indica se la stanza è bloccata */
    private boolean locked;
    
    /** Lista di tutte le stanze del gioco */
    public static List<Room> gameRooms = new ArrayList<>();
    
     /**
     * Costruttore di una stanza.
     *
     * @param roomName  nome della stanza
     * @param roomDescription descrizione della stanza
     * @param objects lista di oggetti presenti
     */
    
    public Room(String roomName, String roomDescription, List<GameObject> objects) {
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.objects = (objects != null) ? new ArrayList<>(objects) : new ArrayList<>();
        this.hidden = false;
        this.locked = false;
    }
    
     /**
     * Recupera una stanza in base al nome.
     *
     * @param name nome della stanza da cercare
     * @return la stanza se trovata, null altrimenti
     */
    
    public static Room findRoom(String name) {
        for (Room r : gameRooms) {
            if (r.getRoomName().equalsIgnoreCase(name)) {
                return r;
            }
        }
        return null;
    }

    // ==== Getter e Setter ====

    public String getRoomName() {
        return this.roomName;
    }

    public String getRoomDescription() {
        return this.roomDescription;
    }

    public List<GameObject> getObjects() {
        return this.objects;
    }
    
    public boolean isHidden() {
           return hidden;
    }
    
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
    
    public boolean isLocked() {
        return locked;
    }
    
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    
    /**
     * Aggiunge uno o più oggetti alla stanza.
     *
     * @param objs oggetti da aggiungere
     */
    public void addObjects(GameObject... objs) {
        this.objects.addAll(Arrays.asList(objs));
    }

    /**
     * Rimuove un oggetto dalla stanza in base al nome.
     *
     * @param name nome dell'oggetto da rimuovere
     */
    public void removeObjcet(String name) {
        this.objects.removeIf(item -> item.getName().equalsIgnoreCase(name));
    }
    
    //public void showDescription() {
    //     DatabaseConnection.printFromDB("Osserva", roomName, "0", "0", "0");
    //    }
}
