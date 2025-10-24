/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.interactionManager;

/**
 *
 */
public class UserInputManager {

    private static String userInput = "";

    public static synchronized String getUserInput() {
        String temp = userInput;
        userInput = "";
        return temp;
    }

    public static synchronized void setUserInput(String userInput) {
        UserInputManager.userInput = userInput;
    }

    public static synchronized boolean isUserInputEmpty() {
        return userInput.isEmpty();
    }

     public static void startInputListener() {
        new Thread(() -> {
            while (true) {
                if (!isUserInputEmpty()) {
                    UserInputFlow.gameFlow(getUserInput());
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
