/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.logic;

import it.uniba.lacasadicenere.entity.Game;
import it.uniba.lacasadicenere.entity.Item;
import it.uniba.lacasadicenere.entity.ItemContainer;


/**
 * Classe per la gestione della logica di gioco.
 */
public class GameLogic {
    
    /**
     * Riferimento all'istanza del gioco.
     */
    private Game game;
    
    /**
     * Costrutture della classe GameLogic
     * @param game 
     */
    public GameLogic(Game game) {
        this.game = game;
    }
    
    /**
     * Metodo per eseguire l'uso di un singolo oggetto.
     * @param i
     * @return 
     */
    public boolean executeUseSingeItem(Item i) {
        if((i.hasName("Candela") || i.hasName("Foglio")) &&
                game.getCurrentRoom().getName().equals("Stanza1")) {
            
            game.unlockCorridor("Stanza1", "Stanza2");
            return true;
        }
        
        if(i.hasName("Diario") && 
                game.getCurrentRoom().getName().equals("Stanza3")) {
            
            game.unlockCorridor("Stanza3", "Stanza4");
            return true;
        }
        return false;
    }
    
    public void executePostPickupEffects(Item i, ItemContainer parentContainer) {
        if (i.hasName("Amuleto") && parentContainer.hasName("Scrigno")) {
            game.unlockCorridor("Stanza2", "Stanza3");
        }
    }
}
