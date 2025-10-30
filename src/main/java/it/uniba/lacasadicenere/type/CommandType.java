/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.type;

/**
 * Classe enumerativa che definisce l'insieme dei comandi validi
 */
public enum CommandType {
    
    /**
     * Comando per muoversi verso Nord
     */
    NORD, 
    
    /**
     * Comando per muoversi verso Sud
     */
    SUD,
    
    /**
     * Comando per muoversi verso Est
     */
    EST,
    
    /**
     * Comando per muoversi verso Ovest
     */
    OVEST, 
    
    /**
     * Comando per osservare una stanza o un oggetto
     */
    OSSERVA,
    
    /**
     * Comando per prendere un oggetto
     */
    PRENDI,
    
    /**
     * Comando per usare un oggetto
     */
    USA, 
    
    /**
     * Comando per lasciare un oggetto
     */
    LASCIA
}
