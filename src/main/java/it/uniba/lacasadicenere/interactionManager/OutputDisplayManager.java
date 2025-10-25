/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.interactionManager;

import it.uniba.lacasadicenere.gui.GameGUI;
import java.awt.FontMetrics;

/**
 * Gestisce la visualizzazione del testo nella GUI, 
 * formattando correttamente il testo con word wrapping.
 */
public class OutputDisplayManager {

    private static final int MARGIN = 10; 

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
     * Formatta il testo applicando word wrapping intelligente.
     * Divide il testo per parole, non per caratteri singoli.
     * 
     * @param text Il testo da formattare
     * @return Il testo formattato con a capo appropriati
     */
    private static String formatText(String text) {
        FontMetrics fontMetrics = GameGUI.getTextPaneFontMetrics();
        int maxWidth = GameGUI.getTextPaneWidth() - MARGIN;
        
        if (fontMetrics == null || maxWidth <= 0) {
            return text; 
        }
        
        StringBuilder result = new StringBuilder();
        String[] paragraphs = text.split("\n");
        
        for (int p = 0; p < paragraphs.length; p++) {
            String paragraph = paragraphs[p];
            
            if (paragraph.trim().isEmpty()) {
                result.append("\n");
                continue;
            }
            
            String[] words = paragraph.split("\\s+");
            StringBuilder currentLine = new StringBuilder();
            
            for (String word : words) {
                String testLine = currentLine.length() == 0 
                    ? word 
                    : currentLine + " " + word;
                
                int lineWidth = fontMetrics.stringWidth(testLine);
                
                if (lineWidth > maxWidth && currentLine.length() > 0) {
                    result.append(currentLine).append("\n");
                    currentLine = new StringBuilder(word);
                } else {
                    if (currentLine.length() > 0) {
                        currentLine.append(" ");
                    }
                    currentLine.append(word);
                }
            }
            if (currentLine.length() > 0) {
                result.append(currentLine);
            }
            
            if (p < paragraphs.length - 1) {
                result.append("\n");
            }
        }
        
        return result.toString();
    }
}