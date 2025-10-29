/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.util;

import it.uniba.lacasadicenere.interactionManager.OutputDisplayManager;

/**
 * Classe Thread per gestire effetti di testo sulla GUI (scrittura lenta e pause).
 */
public class EffettiTesto extends Thread {

    private static boolean isWriting = false;
    
    // Enum per definire il tipo di effetto
    public enum TipoEffetto {
        SCRITTURA,
        PAUSA
    }

    private final String testo;
    private final TipoEffetto tipo;
    private final int velocita; // Velocità in ms tra i caratteri o durata lampeggio
    private final int durata;   // Durata della pausa in ms (usato solo per PAUSA)

    /**
     * Costruttore per l'effetto di scrittura lenta.
     * @param testo Il testo da scrivere.
     * @param velocita La pausa in millisecondi tra i caratteri.
     */
    public EffettiTesto(String testo, int velocita) {
        this.testo = testo;
        this.tipo = TipoEffetto.SCRITTURA;
        this.velocita = velocita;
        this.durata = 0; // Non usato
    }
    
    /**
     * Costruttore per l'effetto di pausa.
     * @param durata La durata della pausa in millisecondi.
     */
    public EffettiTesto(int durata) {
        this.testo = ""; 
        this.tipo = TipoEffetto.PAUSA;
        this.velocita = 0;
        this.durata = durata;
    }

    /**
     * Verifica se l'effetto di scrittura è in corso.
     * @return
     */
    public static synchronized boolean isWriting() {
        return isWriting;
    }

    @Override
    public void run() {
        if(tipo == TipoEffetto.SCRITTURA) {
            synchronized(EffettiTesto.class) {
                isWriting = true;
            }
        }

        try {
            switch(tipo) {
                case SCRITTURA -> effettoScritturaGUI();
                case PAUSA -> effettoPausa();
            }
        } finally {
            if(tipo == TipoEffetto.SCRITTURA) {
                synchronized(EffettiTesto.class) {
                    isWriting = false;
                }
            }
        }
    }

    /**
     * Implementazione dell'effetto di scrittura carattere per carattere sulla GUI.
     */
    private void effettoScritturaGUI() {
        for (char c : testo.toCharArray()) {
            OutputDisplayManager.appendChar(String.valueOf(c));
            try {
                Thread.sleep(velocita);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    /**
     * Implementazione dell'effetto di pausa (Thread.sleep).
     */
    private void effettoPausa() {
        try {
            Thread.sleep(durata);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}