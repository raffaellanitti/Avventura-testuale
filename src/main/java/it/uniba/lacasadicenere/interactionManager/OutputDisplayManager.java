/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.interactionManager;

import it.uniba.lacasadicenere.gui.GameGUI;

import java.awt.FontMetrics;

/**
 *
 */
public class OutputDisplayManager {

    private static final FontMetrics fontMetrics = GameGUI.getTextPaneFontMetrics();

    private static final int MAX_WIDTH = GameGUI.getTextPaneWidth();

    public static void displayText(String text) {
        String formattedText = formatText(text);

        GameGUI.displayTextPaneSetText(formattedText);
    }
    
    public static void appendChar(String character) {
        GameGUI.displayTextPaneAppendText(character);
    }

    public static void appendNewLine() {
        GameGUI.displayTextPaneAppendText("\n");
    }

    /**
     * 
     * @param text
     * @return
     */
    private static String formatText(String text) {
        StringBuilder result = new StringBuilder();
        String[] words = text.split("");

        for(String word : words) {
            StringBuilder line = new StringBuilder();
            for(char c : word.toCharArray()) {
                if(fontMetrics.stringWidth(line.toString() + c + fontMetrics.charWidth('-')) > MAX_WIDTH) {
                    result.append(line).append("-\n");
                    line.setLength(0);
                }
                line.append(c);
            }
            result.append(line).append(" ");
        }
        return result.toString();
    }
}
