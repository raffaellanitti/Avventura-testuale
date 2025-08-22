/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.entity;

/**
 *
 * Classe che rappresenta il corridoio tra le stanze
 */
public class Corridor {
    
    /** Stanza di partenza */
    private Room startingRoom;
  
    /** Stanza di arrivo */
    private Room arrivingRoom;
    
    /** Direzione */
    private CommandType direction;
    
    // ==== Getter e Setter ====
    
    public Room getStartingRoom() {
        return startingRoom;
    }
    public void setStartingRoom(Room room) {
        this.startingRoom = room;
    }
    
    public CommandType getDirection() {
        return direction;
    }
    public void setDirection(CommandType direction) {
        this.direction = direction;
    }
    
    public Room getArrivingRoom() {
        return arrivingRoom;
    }
    public void setArrivingRoom(Room room) {
        this.arrivingRoom = room;
    }
    
}
