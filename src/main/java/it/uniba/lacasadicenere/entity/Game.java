/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.entity;

import it.uniba.lacasadicenere.gui.GameGUI;
import it.uniba.lacasadicenere.interactionManager.OutputDisplayManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta il gioco
 */
public class Game {
    
    /**
     * Inventario del giocatore
     */
    private List<Item> inventory;
    
    /**
     * Stanza in cui si trova il giocatore
     */
    private Room currentRoom;
    
    /**
     * 
     */
    private String currentTime;
    
    /**
     * Lista di tutti i collegamenti tra le stanza
     */
    private List<Corridor> corridorMap;
    
    /**
     * Istanza statica e privata della classe Game stessa
     */
    private static Game game = new Game();
    
    /**
     * Costruttore del gioco
     */
    public Game() {
        this.inventory = new ArrayList<>();
        this.corridorMap = new ArrayList<>();
    }
    
    /**
     * Imposta l'istanza Singleton
     * @param game 
     */
    public static void setUpGame(Game game) {
        Game.game = game;
    }
    
    /**
     * Metodo di accesso all'unica instanza di Game
     * @return game
     */
    public static Game getInstance() {
        return game;
    }

    /**
     * @return inventory
     */
    public List<Item> getInventory() {
        return this.inventory;
    }

    /**
     * Aggiunge un oggetto all'inventario del giocatore
     * @param item 
     */
    public void addInventory(Item item) {
        game.inventory.add(item);
        List<String> itemsNames = game.inventory.stream().map(Item::getName).toList();
        String[] itemsNamesArray = itemsNames.toArray(new String[0]);
        GameGUI.updateInventoryTextArea(itemsNamesArray);
    }

    /** 
     * Rimuove un oggetto dall'inventario del giocatore
     * @param item 
     */
    public void removeInventory(Item item) {
        game.inventory.remove(item);
        List<String> itemsNames = game.inventory.stream().map(Item::getName).toList();
        String[] itemsNamesArray = itemsNames.toArray(new String[0]);
        GameGUI.updateInventoryTextArea(itemsNamesArray);
    }

    /**
     * Stampa la lista degli oggetti presenti nell'inventario
     */
    public void printInventory() {
        OutputDisplayManager.displayText("Inventario: ");
        for (Item item : game.inventory) {
            OutputDisplayManager.displayText("- " + item.getName());
        }
    }

    /**
     * @return currentRoom
     */
    public Room getCurrentRoom() {
        return game.currentRoom;
    }

    /**
     * Imposta la stanza corrente del giocatore
     * @param room 
     */
    public void setCurrentRoom(Room room) {
        if (game.corridorMap != null) {
            for (Corridor corridor : game.corridorMap) {
                if (corridor.getStartingRoom().equals(room)) {
                    game.currentRoom = corridor.getStartingRoom();
                    GameGUI.setImagePanel(game.currentRoom.getName());
                    return;
                }
            }
        }
        game.currentRoom = room;
        GameGUI.setImagePanel(game.currentRoom.getName());
    }
    
    /**
     * 
     * @return 
     */
    public String getCurrentTime() {
        return game.currentTime;
    }

    /**
     *
     * @param currentTime 
     */
    public void setCurrentTime(String currentTime) {
        game.currentTime = currentTime;
    }


    /**
     * @return corridorMap
     */
    public List<Corridor> getCorridorMap() {
        return game.corridorMap;
    }

    /**
     * Imposta la mappa dei corridoi
     * @param corridorsMap 
     */
    public void setCorridorMap(List<Corridor> corridorsMap) {
        game.corridorMap = corridorsMap;
    }

    /**
     * Sblocca un corridoi specifico
     * @param r1 startingRoom
     * @param r2 arrivingRoom
     */
    public void unlockCorridor(String r1, String r2) {
        for (Corridor corridor : game.corridorMap) {
            if (corridor.getStartingRoom().getName().equals(r1) && corridor.getArrivingRoom().getName().equals(r2)) {
                corridor.setLocked(false);
            }
        }
    }
}
