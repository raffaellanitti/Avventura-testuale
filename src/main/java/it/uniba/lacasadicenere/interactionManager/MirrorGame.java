package it.uniba.lacasadicenere.interactionManager;

import it.uniba.lacasadicenere.entity.Game;
import it.uniba.lacasadicenere.database.DatabaseConnection;
import it.uniba.lacasadicenere.gui.GameGUI;

public class MirrorGame {

    private static final String[] VALID_TRUE_ANSWERS = {
        "13", "tredici"
    };

    private static final int CORRECT_NUMBER = 13;
    private static boolean solved = false;
    private static MirrorGame instance;

    private static final String QUESTION = "Quanti specchi ci sono davanti a te nella stanza?";

    private MirrorGame() { }

    public static MirrorGame getInstance() {
        if (instance == null) {
            instance = new MirrorGame();
        }
        return instance;
    }

    public void startGame() {
        Game game = Game.getInstance();
        GameGUI.setImagePanel(game.getCurrentRoom().getName());
        
        DatabaseConnection.printFromDB("Osserva", game.getCurrentRoom().getName(), "true", "0");
        
        OutputDisplayManager.displayText(QUESTION);
        OutputDisplayManager.displayText("Scrivi il numero di specchi che vedi:");
    
        UserInputFlow.Event = 1;
    }

    public void checkAnswer(String answer) {
        if (answer == null || answer.trim().isEmpty()) {
            OutputDisplayManager.displayText("Non hai inserito nulla!");
            return;
        }

        String cleanedAnswer = answer.trim().toLowerCase();
        Game game = Game.getInstance();

        if (isCorrectAnswer(cleanedAnswer)) {

            solved = true;
            DatabaseConnection.printFromDB("0", "Stanza4", "Corretto", "0");
            
            game.unlockCorridor("Stanza4", "Stanza5");
            
            OutputDisplayManager.displayText("Hai risposto correttamente! Il passaggio a nord è ora aperto.");
            
            UserInputFlow.Event = 0;

        } else {
            DatabaseConnection.printFromDB("0", "Stanza4", "Sbagliato", "0");

            OutputDisplayManager.displayText("Risposta errata. Osserva meglio gli specchi e riprova.");
        }
    }

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

    public boolean isSolved() {
        return solved;
    }
    
    public static void reset() {
        solved = false;
        instance = null;
    }
}
