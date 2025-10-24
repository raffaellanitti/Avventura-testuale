/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.gui;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.io.File;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.CardLayout;
import java.awt.Image;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 */
public class ManagerGUI extends JFrame {
    static GameGUI game;
    
    public ManagerGUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("La Casa di Cenere");
        setPreferredSize(new Dimension(800, 600));
        setResizable(false);
        try {
            Image icona = ImageIO.read(new File("src/main/img/icona.png"));
            setIconImage(icona);
        } catch (IOException | IllegalArgumentException | NullPointerException e) {
            System.err.println("FATAL WARNING: Immagine icona.png non trovata. Avvio senza icona.");
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
