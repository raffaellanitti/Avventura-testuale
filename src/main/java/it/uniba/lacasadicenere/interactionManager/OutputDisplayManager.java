/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.interactionManager;

import it.uniba.lacasadicenere.gui.GameGUI;
import it.uniba.lacasadicenere.util.EffettiTesto;

import java.awt.FontMetrics;

/**
 * Gestisce la visualizzazione del testo nella GUI, 
 * formattando correttamente il testo con word wrapping.
 */
public class OutputDisplayManager {

    private static final int MARGIN = 10; 
    private static final Object DISPLAY_LOCK = new Object(); // Mutex globale

    /**
     * Visualizza il testo formattato nella GUI.
     * @param text
     */
    public static void displayText(String text) {
        synchronized (DISPLAY_LOCK) {
            // Aspetta che eventuali effetti precedenti finiscano
            while (EffettiTesto.isWriting()) {
                try {
                    DISPLAY_LOCK.wait(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            
            String formattedText = formatText(text);
            EffettiTesto effetto = new EffettiTesto(formattedText + "\n", 30);
            effetto.start();
            
            // Aspetta che l'effetto finisca prima di sbloccare
            while (EffettiTesto.isWriting()) {
                try {
                    DISPLAY_LOCK.wait(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
    
    /**
     * Visualizza il testo immediatamente, senza effetti.
     * Usato per i comandi inseriti dall'utente.
     * 
     * @param text Il testo da visualizzare immediatamente
     */
    public static void displayTextImmediate(String text) {
        synchronized (DISPLAY_LOCK) {
            // Aspetta che eventuali effetti precedenti finiscano
            while (EffettiTesto.isWriting()) {
                try {
                    DISPLAY_LOCK.wait(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            
            GameGUI.displayTextPaneAppendText(text + "\n");
        }
    }

    /**
     * Aggiunge il testo formattato alla GUI senza cancellare il testo esistente.
     * @param character
     */
    public static void appendChar(String character) {
        GameGUI.displayTextPaneAppendText(character);
    }

    /**
     * Aggiunge una nuova linea alla GUI.
     */
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