/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.CardLayout;
import java.awt.Image;

/**
 * Gestore principale della GUI con correzioni per il caricamento delle risorse
 */
public class ManagerGUI extends JFrame {
    static GameGUI game;
    
    public ManagerGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("La Casa di Cenere");
        setPreferredSize(new Dimension(800, 600));
        setResizable(false);
        
        // Carica l'icona dal classpath
        try {
            java.net.URL iconURL = getClass().getResource("/img/icona.png");
            if (iconURL != null) {
                Image icona = new ImageIcon(iconURL).getImage();
                setIconImage(icona);
            } else {
                System.err.println("ATTENZIONE: Icona non trovata nel classpath (/img/icona.png)");
                // Prova con percorso alternativo per compatibilità
                try {
                    Image icona = new ImageIcon("src/main/resources/img/icona.png").getImage();
                    setIconImage(icona);
                } catch (Exception e2) {
                    System.err.println("Impossibile caricare l'icona anche con percorso alternativo. Avvio senza icona.");
                }
            }
        } catch (Exception e) {
            System.err.println("ERRORE durante il caricamento dell'icona: " + e.getMessage());
        }
        
        JPanel cards = new JPanel(new CardLayout());
        MenuGUI menu = new MenuGUI();
        CreditsGUI credits = new CreditsGUI();
        game = new GameGUI();

        cards.add(menu, "MenuGUI");
        cards.add(credits, "CreditsGUI");
        cards.add(game, "GameGUI");

        add(cards);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void closeGame() {
        game.goBack();
    }
}