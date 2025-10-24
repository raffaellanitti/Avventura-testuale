/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.gui;

import it.uniba.lacasadicenere.entity.Game;
import it.uniba.lacasadicenere.entity.Item;
import it.uniba.lacasadicenere.logic.GameManager;
import it.uniba.lacasadicenere.interactionManager.UserInputFlow;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.imageio.ImageIO;

/**
 *
 */
public class MenuGUI extends JPanel {
    
    private JPanel backgroundPanel;
    
    private JButton newGame;
    
    private JButton help;
    
    private JButton loadGame;
    
    private JButton credits;
    
    GameManager gameManager = new GameManager();
    
    public MenuGUI() {
        initComponents();
    }
    
    private void initComponents() {
        
        // DEFINIZIONE COLORI 
        final Color COLD_LIGHT = new Color(200, 220, 255); 
        final Color SEMI_TRANSPARENT_BG = new Color(50, 60, 70, 60); 
        final Color COLD_SELECT_COLOR = new Color(100, 120, 140, 100); 
        final Color FOG_BACKGROUND = new Color(30, 30, 35);
        
        // Sostituisci il metodo paintComponent del backgroundPanel in MenuGUI.java
        backgroundPanel = new JPanel() {
            private final Image image;

        { // Blocco di inizializzazione per caricare l'immagine una sola volta
            Image tempImage = null;
            try {
            // Carica dal classpath (da src/main/resources/img/)
                tempImage = ImageIO.read(MenuGUI.class.getResourceAsStream("/img/sfondo.png")); 
            } catch (IOException | IllegalArgumentException | NullPointerException e) {
                System.err.println("Errore: Immagine sfondo.png non trovata.");
            }
            this.image = tempImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        }
    };
        newGame = new JButton();
        newGame.setAlignmentX(Component.LEFT_ALIGNMENT);
        help = new JButton();
        help.setText("?");
        loadGame = new JButton();
        credits = new JButton();
        
        setPreferredSize(new Dimension(800, 600));
        setSize(new Dimension(800, 600));
        
        backgroundPanel.setMinimumSize(new Dimension(800, 600));
        backgroundPanel.setPreferredSize(new Dimension(800, 600));
        backgroundPanel.setRequestFocusEnabled(false);
        backgroundPanel.setBackground(FOG_BACKGROUND);
        
        newGame.setUI(new MetalButtonUI() {
               protected Color getSelectColor() {
                   return COLD_SELECT_COLOR;
               }
        });
        newGame.setFocusPainted(false);
        newGame.setBackground(SEMI_TRANSPARENT_BG);
        newGame.setForeground(COLD_LIGHT);
        newGame.setFont(new Font("Otacon", 1, 24));
        newGame.setBorderPainted(true);
        newGame.setBorder(BorderFactory.createLineBorder(COLD_LIGHT, 3));
        newGame.setText("NUOVA PARTITA");
        newGame.setOpaque(true);
        newGame.setContentAreaFilled(true);
        
        newGame.setMaximumSize(new Dimension(240, 60));
        newGame.setMinimumSize(new Dimension(240, 60));
        newGame.setPreferredSize(new Dimension(240, 60));
        newGame.addActionListener(this::newGameActionPerformed);
        
        help.setUI(new MetalButtonUI() {
            protected Color getSelectColor () {
                return COLD_SELECT_COLOR;

            }
        });
        help.setFocusPainted(false);
        help.setBackground(SEMI_TRANSPARENT_BG);
        help.setForeground(COLD_LIGHT);
        help.setFont(new Font("Otacon", 1, 24));
        help.setBorderPainted(true);
        help.setBorder(BorderFactory.createLineBorder(COLD_LIGHT,2));
        help.setMargin(new Insets(0, 0, 0, 0));
        help.setMaximumSize(new Dimension(40, 40));
        help.setMinimumSize(new Dimension(40, 40));
        help.setPreferredSize(new Dimension(40, 40));
        help.setOpaque(true);
        help.setContentAreaFilled(true);
        help.addActionListener(this::helpActionPerformed);

        loadGame.setUI(new MetalButtonUI() {
            protected Color getSelectColor () {
                return COLD_SELECT_COLOR;

            }
        });
        loadGame.setFocusPainted(false);
        loadGame.setBackground(SEMI_TRANSPARENT_BG);
        loadGame.setForeground(COLD_LIGHT);
        loadGame.setFont(new Font("Otacon", 1, 24));
        loadGame.setBorderPainted(true);
        loadGame.setBorder(BorderFactory.createLineBorder(COLD_LIGHT, 3));
        loadGame.setText("CARICA PARTITA");
        loadGame.setMaximumSize(new Dimension(240, 60));
        loadGame.setMinimumSize(new Dimension(240, 60));
        loadGame.setPreferredSize(new Dimension(240, 60));
        loadGame.setOpaque(true);
        loadGame.setContentAreaFilled(true);
        loadGame.addActionListener(evt -> {
            try {
                loadGameActionPerformed(evt);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        credits.setUI(new MetalButtonUI() {
            protected Color getSelectColor () {
                return COLD_SELECT_COLOR;

            }
        });
        credits.setFocusPainted(false);
        credits.setBackground(SEMI_TRANSPARENT_BG);
        credits.setForeground(COLD_LIGHT);
        credits.setFont(new Font("Otacon", 1, 24));
        credits.setBorderPainted(true);
        credits.setBorder(BorderFactory.createLineBorder(COLD_LIGHT, 3));
        credits.setText("RICONOSCIMENTI");
        credits.setMaximumSize(new Dimension(240, 60));
        credits.setMinimumSize(new Dimension(240, 60));
        credits.setPreferredSize(new Dimension(240, 60));
        credits.setOpaque(true);
        credits.setContentAreaFilled(true);
        credits.addActionListener(this::creditsActionPerformed);

        GroupLayout backgroundPanelLayout = new GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        
        final int BUTTON_WIDTH = 240;
        final int HORIZONTAL_GAP = 50; 
        final int TOTAL_BUTTONS_WIDTH = (BUTTON_WIDTH * 2) + HORIZONTAL_GAP; // 530px
        final int HORIZONTAL_PADDING = (800 - TOTAL_BUTTONS_WIDTH) / 2; // (800 - 530) / 2 = 135px
        
        backgroundPanelLayout.setHorizontalGroup(
                backgroundPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                .addGap(25)
                                .addComponent(help, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(credits, GroupLayout.PREFERRED_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE)
                                .addGap(25))
                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                .addGap(25)
                                .addGap(HORIZONTAL_PADDING) 
                                .addComponent(newGame, GroupLayout.PREFERRED_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE)
                                .addGap(HORIZONTAL_GAP) 
                                .addComponent(loadGame, GroupLayout.PREFERRED_SIZE, BUTTON_WIDTH, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(HORIZONTAL_PADDING, Short.MAX_VALUE)) 
        );
        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(backgroundPanelLayout.createSequentialGroup()
                    .addGap(25) 
                    .addGroup(backgroundPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(help, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                        .addComponent(credits, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)) 
                    .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(backgroundPanelLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(newGame, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                        .addComponent(loadGame, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                    .addGap(40)) 
        );
        backgroundPanel.setLayout(backgroundPanelLayout);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(backgroundPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(backgroundPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }
    
    private void newGameActionPerformed(ActionEvent evt) {
        
        gameManager.createGame();
        Game game = Game.getInstance();
        
        new Thread(() -> UserInputFlow.setUpGameFlow(game)).start();
        
        CardLayout cl = (CardLayout) getParent().getLayout();
        cl.show(getParent(), "GameGUI");
        
        Timer timer = new Timer(1000, e -> {
            GameGUI.updateInventoryTextArea(
            Game.getInstance().getInventory().stream()
                    .map(Item::getName)
                    .toArray(String[]::new)
            );
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void helpActionPerformed(ActionEvent evt) {
        try {
            HelpGUI helpGUI = HelpGUI.getInstance();
            
            helpGUI.setLocationRelativeTo(null);
            
            helpGUI.setVisible(true);
        } catch(Exception e) {
            showMessageDialog(this, "Errore nell'apertura della guida", "Errore", ERROR_MESSAGE);
        }
    }
    
    private void loadGameActionPerformed(ActionEvent evt) throws IOException, ClassNotFoundException {
        gameManager.resetItems();
        boolean loadedGameSuccessfully = gameManager.loadGame();
        
        if(loadedGameSuccessfully) {
            Game game = Game.getInstance();
            CardLayout cl = (CardLayout) getParent().getLayout();
            cl.show(getParent(), "GameGUI");
            
            new Thread(() -> UserInputFlow.setUpLoadedGameFlow(game)).start();
        } else {
            showMessageDialog(null, "Nessuna partita salvata trovata.", "Errore", ERROR_MESSAGE);
        }
    }
    
    private void creditsActionPerformed(ActionEvent evt) {
        CardLayout cl = (CardLayout) getParent().getLayout();
        cl.show(getParent(), "CreditsGUI");
    }
}
