/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.logic;

import it.uniba.lacasadicenere.entity.*;
import it.uniba.lacasadicenere.type.CommandType;
import it.uniba.lacasadicenere.util.Converter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Classe per la gestione del gioco.
 */
public class GameManager {
    
    /**
     * Mappa degli oggetti di gioco.
     */
    private static Map<String, Item> items = new HashMap<>();
    
    /**
     * Riferimento all'istanza del convertitore.
     */
    private final Converter converter = new Converter();
    
    /**
     * Inizializza una nuova istanza di GameManager.
     */
    public void createGame() {
        items = converter.convertJsonToJavaClass();
    }
    
    /**
     * Metodo per salvare lo stato di gioco in un file JSON.
     */
    public void saveGame() {
        converter.convertGameToJson();
        converter.convertItemsToJson();
    }
    
    /**
     * Metodo per caricare lo stato di gioco da un file JSON.
     * @return
     */
    public boolean loadGame() {
        items = converter.loadGame();
        
        try {
            items.get(1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Metodo per ottenere l'insieme degli oggetti di gioco.
     * @return
     */
    public Set<Item> getItems() {
        Set<Item> items = new HashSet<>();
        Game game = Game.getInstance();
        
        if(game == null || game.getCorridorMap() == null) {
            return items;
        }
        
        Set<Room> rooms = game.getCorridorMap().stream()
                .flatMap(c -> Stream.of(c.getStartingRoom(), c.getArrivingRoom()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        for(Room room : rooms) {
            List<Item> roomItems = room.getItems();
            if(roomItems != null) {
                items.addAll(roomItems);
            }
        }
        
        Set<Item> allItems = new HashSet<>(items);
        addContainedItemsRecursively(allItems, items);
        
        return allItems;
    }
    
    /**
     * Metodo ricorsivo per aggiungere gli oggetti contenuti.
     * @param allItems
     * @param currentItems
     */
     private void addContainedItemsRecursively(Set<Item> allItems, Set<Item> currentItems) {
        Set<Item> newlyFoundItems = new HashSet<>();
        
        for(Item item : currentItems) {
            if(item instanceof ItemContainer) {
                ItemContainer container = (ItemContainer) item;
                List<Item> containedList = container.getList(); 
                
                if(containedList != null) {
                    for(Item contained : containedList) {
                        if(allItems.add(contained)) {
                            newlyFoundItems.add(contained);
                        }
                    }
                }
            }
        }

        if (!newlyFoundItems.isEmpty()) {
            addContainedItemsRecursively(allItems, newlyFoundItems);
        }
    }
    
    /**
     * Metodo per istanziare comandi di gioco.
     * @return
     */
    public Set<Command> getAllCommands() {
        Set<Command> availableCommands = new HashSet<>();
        
        availableCommands.add(new Command("Nord", List.of("north", "avanti", "vaiAvanti"), CommandType.NORD));
        availableCommands.add(new Command("Sud", List.of("south", "indietro", "vaiIndietro"), CommandType.SUD));
        availableCommands.add(new Command("Est", List.of("east", "destra", "vaiDestra", "vaiADestra"), CommandType.EST));
        availableCommands.add(new Command("Ovest", List.of("west", "sinsitra", "vaiSinistra", "vaiASinistra"), CommandType.OVEST));
        availableCommands.add(new Command("Osserva", List.of("vedi", "guarda", "esamina", "look"), CommandType.OSSERVA));
        availableCommands.add(new Command("Inventario", List.of("i", "inventory", "inv"), CommandType.INVENTARIO));
        availableCommands.add(new Command("Prendi", List.of("raccogli", "take", "recupera"), CommandType.PRENDI));
        availableCommands.add(new Command("Usa", List.of("use", "utilizza"), CommandType.USA));
        availableCommands.add(new Command("Lascia", List.of("rilascia", "poni", "togli"), CommandType.LASCIA));
        
        return availableCommands;
    }
    
    /**
     * Resetta tutti gli oggetti di gioco.
     */
    public void resetItems() {
        items = new HashMap<>();
    }
}
