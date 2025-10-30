/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.interactionManager;

/**
 * Gestisce l'input dell'utente.
 */
public class GameFlowController {

    private static String userInput = "";

    /**
     * Restituisce l'input corrente e svuota il buffer
     * Metodo thread-safe: l'accesso è sincronizzato per evitare condizioni di gara.
     *
     * @return l'input utente corrente; stringa vuota se non presente
     */
    public static synchronized String getUserInput() {
        String temp = userInput;
        userInput = "";
        return temp;
    }

    /**
     * Imposta l'input utente nel buffer
     * @param userInput 
     */
    public static synchronized void setUserInput(String userInput) {
        GameFlowController.userInput = userInput;
    }

    /**
     * Verifica se l'input utente è vuoto
     * @return true se l'input è vuoto, false altrimenti
     */
    public static synchronized boolean isUserInputEmpty() {
        return userInput.isEmpty();
    }

    /**
     * Avvia un thread che ascolta l'input utente e avvia il flusso di gioco quando l'input è disponibile.
     */
    public static void startInputListener() {
        new Thread(() -> {
            while (true) {
                if (!isUserInputEmpty()) {
                    InputService.gameFlow(getUserInput());
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}