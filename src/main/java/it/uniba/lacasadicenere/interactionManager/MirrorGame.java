package it.uniba.lacasadicenere.interactionManager;

import it.uniba.lacasadicenere.entity.Game;
import it.uniba.lacasadicenere.database.DatabaseH2;
import it.uniba.lacasadicenere.ui.GamePanel;

/**
 * Classe che gestisce il gioco degli specchi.
 */
public class MirrorGame {

    private static final String[] VALID_TRUE_ANSWERS = {
        "13", "tredici"
    };

    private static final int CORRECT_NUMBER = 13;
    private static boolean solved = false;
    private static MirrorGame instance;

    private static final String QUESTION = "Quanti specchi ci sono davanti a te nella stanza?";

    // Costruttore privato per il pattern singleton: impedisce la creazione di istanze esterne.
    private MirrorGame() { }

    /**
     * Restituisce l'istanza singleton di MirrorGame.
     * @return
     */
    public static MirrorGame getInstance() {
        if (instance == null) {
            instance = new MirrorGame();
        }
        return instance;
    }

    /**
     * Inizia il gioco degli specchi.
     */
    public void startGame() {
        Game game = Game.getInstance();
        GamePanel.setImagePanel(game.getCurrentRoom().getName());
        
        DatabaseH2.printFromDB("Usa", game.getCurrentRoom().getName(), 
        "true", "Telefono", "0"); 
        
        GameFlowController.getUserInput();

        OutputService.displayText(QUESTION + "\nScrivi il numero di specchi che vedi:");

        InputService.Event = 1;
    }

    /**
     * Controlla la risposta data dall'utente.
     * @param answer
     */
    public void checkAnswer(String answer) {
        if (answer == null || answer.trim().isEmpty()) {
            OutputService.displayText("Non hai inserito nulla!");
            return;
        }

        String cleanedAnswer = answer.trim().toLowerCase();
        Game game = Game.getInstance();

        if (isCorrectAnswer(cleanedAnswer)) {

            solved = true;
            DatabaseH2.printFromDB("0", "Stanza4", "Corretto", "0", "0");
            
            game.unlockCorridor("Stanza4", "Stanza5");
            
            InputService.Event = 0;

        } else {
            DatabaseH2.printFromDB("0", "Stanza4", "Sbagliato", "0", "0");
        }
    }

    /**
     * Controlla se la risposta è corretta.
     * @param text
     * @return
     */
    private boolean isCorrectAnswer(String text) {
        for (String valid : VALID_TRUE_ANSWERS) {
            if (text.equals(valid)) {
                return true;
            }
        }

        try {
            int num = Integer.parseInt(text);
            return num == CORRECT_NUMBER;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Verifica se il gioco è stato risolto.
     * @return
     */
    public boolean isSolved() {
        return solved;
    }
    
    /**
     * Resetta lo stato del gioco.
     */
    public static void reset() {
        solved = false;
        instance = null;
    }
}