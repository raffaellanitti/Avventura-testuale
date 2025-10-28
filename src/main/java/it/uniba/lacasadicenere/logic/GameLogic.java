
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.logic;

import it.uniba.lacasadicenere.entity.Game;
import it.uniba.lacasadicenere.entity.Item;
import it.uniba.lacasadicenere.entity.ItemContainer;
import it.uniba.lacasadicenere.interactionManager.OutputDisplayManager;
import it.uniba.lacasadicenere.database.DatabaseConnection;
import it.uniba.lacasadicenere.interactionManager.MirrorGame;
import it.uniba.lacasadicenere.interactionManager.UserInputFlow;


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
    public boolean executeUseSingleItem(Item i) {
        if(i.hasName("Telefono") && game.getCurrentRoom().getName().equals("Stanza4")) {
            MirrorGame mirrorGame = MirrorGame.getInstance();
            mirrorGame.startGame();
            return true;
        }
        return false;
    }

    /**
     * Metodo per eseguire l'uso di due oggetti.
     * @param item1
     * @param item2
     * @return
     */
    public boolean executeUseDoubleItem(Item item1, Item item2) {
        if(item1.hasName("Fiammiferi") && item2.hasName("Candela") && game.getCurrentRoom().getName().equals("Stanza1")) {
            if (!game.getInventory().contains(item2)) {
                return false; 
            }
            game.removeInventory(item1);
             DatabaseConnection.printFromDB("Usa", game.getCurrentRoom().getName(), 
            "true", "Fiammiferi", "Candela");
             game.unlockCorridor("Stanza1", "Stanza2");
            return true;
        }

        if(item1.hasName("Chiave") && item2.hasName("Scrigno") && game.getCurrentRoom().getName().equals("Stanza2")) {
            Item scrigno = game.getCurrentRoom().getItems().stream()
                    .filter(item -> item.hasName("Scrigno"))
                    .findFirst()
                    .orElse(null);

                    if(scrigno == null || !(scrigno instanceof ItemContainer)) {
                        return false; 
                    }

                    ItemContainer scrignoContainer = (ItemContainer) scrigno;
                    if(scrignoContainer.getList() == null || scrignoContainer.getList().isEmpty()) {
                        return false; 
                    }
            game.removeInventory(item1);
            DatabaseConnection.printFromDB("Usa", game.getCurrentRoom().getName(), 
            "true", "Chiave", "Scrigno");
            return true;
        }
        return false;
    }

    /**
     * Metodo per eseguire gli effetti post-raccolta di un oggetto.
     * @param i
     * @param parentContainer
     */
    public void executePostPickupEffects(Item i, ItemContainer parentContainer) {
        if (i.hasName("Amuleto") && parentContainer.hasName("Scrigno")) {
            game.unlockCorridor("Stanza2", "Stanza3");
        }
    }
    
    /**
     * Metodo per eseguire gli effetti post-raccolta di un oggetto.
     * @param i
     */
    public void executePostPickupEffects(Item i) {
        if (i.hasName("Diario") && game.getCurrentRoom().getName().equals("Stanza3")) {
            game.unlockCorridor("Stanza3", "Stanza4");
        }
    }
    
    /**
     * Metodo per controllare la condizione di fine gioco.
     */
    public void checkEndGameCondition() {
        if (game.getCurrentRoom().getName().equals("Stanza5")) {
            boolean hasCandela = game.getCurrentRoom().getItems().stream().anyMatch(i -> i.hasName("Candela"));
            boolean hasAmuleto = game.getCurrentRoom().getItems().stream().anyMatch(i -> i.hasName("Amuleto"));
            boolean hasDiario = game.getCurrentRoom().getItems().stream().anyMatch(i -> i.hasName("Diario"));

            if (hasCandela && hasAmuleto && hasDiario) {
                UserInputFlow.Event = 2;
                UserInputFlow.gameFlow("");
            }
        }
    }
}