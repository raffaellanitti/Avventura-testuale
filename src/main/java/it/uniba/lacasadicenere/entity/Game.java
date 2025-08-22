/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe che rappresenta il gioco
 * Contiene l'inventario, le stanze e la gestion della stanza
 */
public class Game {
    
    /** Lista di oggetti raccolti dal giocatore */
    private List<GameObject> inventory;
    
    /** Orario attuale di gioco */
    private String gameTime;
    
    /** Stanza in cui si trova il giocatore */
    private Room currentRoom;
    
    /** Lista dei corridoi del gioco */
    private List<Corridor> corridorMap;
    
    /** Stato delle stanze (locked o unlocked) */
    private Map<String, Boolean> roomStatus;
    
    /** Istanza singola del gioco (singleton) */
    private static Game game = new Game();
    
    /** Costruttore principale */
    public Game() {
           this.inventory = new ArrayList<>();
           this.corridorMap = new ArrayList<>();
           this.roomStatus = new HashMap<>();
    }
    
     public static void setUpGame(Game game) {
        Game.game = game;
    }

    public static Game getInstance() {
        return game;
    }
    
    public List<GameObject> getInventory() {
        return inventory;
    }
    
     public void addInventory(GameObject object) {
        game.inventory.add(object);
    }

    public void removeInventory(GameObject object) {
        game.inventory.remove(object);
    }

    public void printInventory() {
        System.out.println("> Inventario:");
        for (GameObject object : game.inventory) {
            System.out.println(">  - " + object.getName());
        }
    }

    public Room getCurrentRoom() {
        return game.currentRoom;
    }

    public void setCurrentRoom(Room room) {
        game.currentRoom = room;
    }
    
    
    public String getGameTime() {
        return game.gameTime;
    }

    public void setGameTime(String gameTime) {
        game.gameTime = gameTime;
    }

    public List<Corridor> getCorridorMap() {
        return game.corridorMap;
    }

    public void setCorridorMap(List<Corridor> corridorMap) {
        game.corridorMap = corridorMap;
    }

    /**
     * Controlla se una stanza è bloccata
     * @param roomName nome della stanza
     * @return true se è locked, false altrimenti
     */
    public boolean getRoomStatus(String roomName) {
        Boolean locked = game.roomStatus.get(roomName);
        return locked != null && locked;
    }

    /**
     * Blocca o sblocca una stanza
     * @param roomName nome della stanza
     * @param locked true per bloccare, false per sbloccare
     */

    public void setRoomStatus(String roomName, Boolean locked) {
        if (game.roomStatus == null) {
            game.roomStatus = new HashMap<>();
        }
        game.roomStatus.put(roomName, locked);
        
        Room r = Room.findRoom(roomName);
        if (r != null) {
           r.setLocked(locked);
        }
    }
}
