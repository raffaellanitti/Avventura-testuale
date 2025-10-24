/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.entity;

import it.uniba.lacasadicenere.type.CommandType;

/**
 * Classe che modella una singola connessione direzionale tra due stanze 
 */
public class Corridor {
    
    /**
     * Room di origine
     */
    private Room startingRoom;
     
    /**
     * Room di destinazione
     */
    private Room arrivingRoom;
    
    /**
     * Direzione che il giocatore deve prendere dalla startingRoom 
     * per raggiungere la arrivingRoom
     */
    private CommandType direction;
    
    /**
     * Booleano che indica se il passaggio è bloccato o aperto
     */
    private boolean locked;
    
    /**
     * @return startingRoom
     */
    public Room getStartingRoom() {
        return startingRoom;
    }
    /**
     * @param room 
     */
    public void setStartingRoom(Room room) {
        this.startingRoom = room;
    }
    
    /**
     * @return arrivingRoom
     */
    public Room getArrivingRoom() {
        return arrivingRoom;
    }
    /**
     * @param room 
     */
    public void setArrivingRoom(Room room) {
        this.arrivingRoom = room;
    }
    
    /**
     * @return direction
     */
    public CommandType getDirection() {
        return direction;
    }
    /**
     * @param direction 
     */
    public void setDirection(CommandType direction) {
        this.direction = direction;
    }
   
    /**
     * @return locked
     */
    public boolean isLocked() {
        return locked;
    }
    /**
     * @param locked 
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }  
}
