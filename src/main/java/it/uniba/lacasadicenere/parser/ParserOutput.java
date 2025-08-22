/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.parser;

import it.uniba.lacasadicenere.entity.GameObject;
import it.uniba.lacasadicenere.entity.GameCommand;

/**
 *
 * @author raffaellanitti
 */
public class ParserOutput {
    
    /** Comando identificato dal parser */
    private GameCommand command;
    
    /** Oggetto coinvolto dal comando, se presente */
    private GameObject object;
    
    /**
     * Costruttore principale
     * 
     * @param command Il comando riconosciuto
     * @param object L'oggetto associato al comando
     */
    public ParserOutput(GameCommand command, GameObject object) {
        this.command = command;
        this.object = object;
    }
    
    // ==== Getter e Setter ====
    
    public GameCommand getCommand() {
           return command;
    }
    public void setCommand(GameCommand command) {
        this.command = command;
    }
    
    public GameObject getObject() {
        return object;
    }
    public void setObject(GameObject object) {
        this.object = object;
    }
}
