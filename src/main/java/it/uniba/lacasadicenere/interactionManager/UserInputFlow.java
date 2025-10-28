/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.interactionManager;

import it.uniba.lacasadicenere.database.DatabaseConnection;
import it.uniba.lacasadicenere.entity.Game;
import it.uniba.lacasadicenere.entity.Item;
import it.uniba.lacasadicenere.gui.GameGUI;
import it.uniba.lacasadicenere.gui.ManagerGUI;
import it.uniba.lacasadicenere.logic.CommandExecutor;
import it.uniba.lacasadicenere.logic.Parser;
import it.uniba.lacasadicenere.type.ParserOutput;
import it.uniba.lacasadicenere.util.EffettiTesto;

import java.util.List;

/**
 * Gestisce il flusso di input dell'utente e gli eventi speciali del gioco.
 */
public class UserInputFlow {

    public static int Event;

    private static Parser parser;
    private static CommandExecutor commandExecutor;
    private static MirrorGame mirrorGame;

    /**
     * Punto di ingresso principale per processare l'input dell'utente.
     * Non visualizza l'input dell'utente - lo gestiscono i metodi specifici.
     */
    public static void gameFlow(final String text) {
        switch(Event) {
            case 0:
                parserFlow(text);
                break;
            case 1:
                mirrorGameFlow(text);
                break;
            case 2:
                endingFlow(text);
                break;
            default:
                parserFlow(text);
                break;
        }
    }

    /**
     * Gestisce il flusso normale del parser per i comandi di gioco.
     */
    private static void parserFlow(final String text) {
        if(parser == null) {
            OutputDisplayManager.displayText("Errore nell'inizializzazione del parser. Avviare di nuovo il gioco.");
            return;
        }
        ParserOutput parserOutput = parser.parse(text);

        if(parserOutput.getCommand() != null) {
            commandExecutor.execute(parserOutput);
        } else {
            OutputDisplayManager.displayText("Comando non riconosciuto. Riprova.");
        }
    }
    
    /**
     * Gestisce il mini-gioco degli specchi.
     */
    private static void mirrorGameFlow(final String text) {
        if(mirrorGame == null) {
            OutputDisplayManager.displayText("Errore: gioco non inizializzato.");
            Event = 0;
            return;
        }        
        mirrorGame.checkAnswer(text);
    }

    /**
     * Gestisce il finale del gioco quando il giocatore completa tutti gli obiettivi.
     */
    private static void endingFlow(final String text) {
        
        String testo = "Mentre poggi la candela, l'amuleto e il diario sull'altare, un bagliore caldo avvolge la cripta. "
                + "La casa sospira, come liberata da un antico peso. Le ombre svaniscono, i muri anneriti sembrano respirare "
                + "e una sensazione di pace ti avvolge. Hai riportato la luce, la memoria e la protezione… "
                + "e La Casa di Cenere finalmente riposa.";

        OutputDisplayManager.displayText(testo);

        EffettiTesto pausa = new EffettiTesto(10000);
        pausa.start();

        new Thread(() -> {
            try {
                pausa.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            ManagerGUI.closeGame();
            OutputDisplayManager.displayText("Grazie per aver giocato a La Casa di Cenere!");
        }).start();
    }

    /**
     * Configura il flusso di gioco per una nuova partita.
     */
    public static void setUpGameFlow(final Game game) {
        Event = 0;
        if(game.getCurrentRoom() == null) {
            OutputDisplayManager.displayText("Errore nell'inizializzazione della stanza corrente. Avviare di nuovo il gioco.");
            return;
        }
        DatabaseConnection.printFromDB("0", game.getCurrentRoom().getName(), "true", "0", "0");

        mirrorGame = MirrorGame.getInstance();
        parser = new Parser();
        commandExecutor = new CommandExecutor(game);
    }
        
    /**
     * Configura il flusso di gioco per una partita caricata.
     */
    public static void setUpLoadedGameFlow(final Game game) {
        Event = 0;
        
        mirrorGame = MirrorGame.getInstance();
        parser = new Parser();
        commandExecutor = new CommandExecutor(game);
        List<String> itemsNames = game.getInventory().stream().map(Item::getName).toList();
        String[] itemsNamesArray = itemsNames.toArray(new String[0]);
        GameGUI.updateInventoryTextArea(itemsNamesArray);
        if (game.getCurrentRoom().getName().equals("Stanza1")) {
            DatabaseConnection.printFromDB("0", game.getCurrentRoom().getName(), "true", "0", "0");
        }
        else
        {
            DatabaseConnection.printFromDB("Osserva", game.getCurrentRoom().getName(), "true", "0", "0");
        }
    }
}

