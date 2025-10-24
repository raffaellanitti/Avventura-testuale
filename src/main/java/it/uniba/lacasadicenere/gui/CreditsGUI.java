/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.gui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.SwingConstants;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.CardLayout;

/**
 * GUI per la sezione Riconoscimenti.
 */
public class CreditsGUI extends JPanel {
    
    /**
     * Componenti della GUI.
     */
    private JButton goBack;
    private JLabel titleLabel;
    private JPanel backgroundPanel;
    private JLabel contentLabel;

    public CreditsGUI() {
        initComponents();
    }

    private void initComponents() {
        
        // DEFINIZIONE COLORI 
        final Color COLD_LIGHT = new Color(200, 220, 255);
        final Color SEMI_TRANSPARENT_BG = new Color(50, 60, 70, 100); 
        final Color COLD_SELECT_COLOR = new Color(100, 120, 140, 150); 
        final Color FOG_BACKGROUND = new Color(30, 30, 35); 
        final Color DARK_FOG_CONTENT = new Color(45, 50, 55, 220); 
        final Color ACCENT_SHADOW = new Color(10, 10, 15, 150); 

        // Inizializzazione componenti
        backgroundPanel = new JPanel();
        goBack = new JButton();
        titleLabel = new JLabel();
        contentLabel = new JLabel();

        setPreferredSize(new Dimension(800, 600));

        // --- BACKGROUND PANEL ---
        backgroundPanel.setMinimumSize(new Dimension(800, 600));
        backgroundPanel.setPreferredSize(new Dimension(800, 600));
        backgroundPanel.setBackground(FOG_BACKGROUND); 

        // --- PULSANTE TORNA INDIETRO (goBack) ---
        goBack.setUI(new MetalButtonUI() {
            protected Color getSelectColor() {
                return COLD_SELECT_COLOR;
            }
        });
        goBack.setFocusPainted(false);
        goBack.setBackground(SEMI_TRANSPARENT_BG);
        goBack.setForeground(COLD_LIGHT);
        goBack.setBorderPainted(true);
        goBack.setBorder(BorderFactory.createLineBorder(COLD_LIGHT.brighter(), 1)); 
        goBack.setFont(new Font("Monospaced", Font.BOLD, 22)); 
        goBack.setText("❮"); 
        goBack.setMaximumSize(new Dimension(50, 50)); 
        goBack.setMinimumSize(new Dimension(50, 50));
        goBack.setPreferredSize(new Dimension(50, 50));
        goBack.setOpaque(true); 
        goBack.setContentAreaFilled(true);
        goBack.addActionListener(this::goBackActionPerformed);
        
        // --- ETICHETTA TITOLO (titleLabel) ---
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 36)); 
        titleLabel.setForeground(COLD_LIGHT);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(DARK_FOG_CONTENT);
        titleLabel.setText("— Riconoscimenti —"); 
        titleLabel.setPreferredSize(new Dimension(400, 55)); 
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createLineBorder(COLD_LIGHT, 2)); 

        // --- ETICHETTA CONTENUTO (contentLabel) ---
        contentLabel.setBackground(DARK_FOG_CONTENT);
        contentLabel.setForeground(COLD_LIGHT);
        contentLabel.setOpaque(true);
        contentLabel.setVerticalAlignment(SwingConstants.CENTER); 
        contentLabel.setFont(new Font("Monospaced", Font.PLAIN, 18)); 
        
        // HTML 
        contentLabel.setText(
            "<html><center style='padding: 10px 0;'>" 
            + "<h2 style='font-size: 30px; color: " + toHex(COLD_LIGHT) + "; text-shadow: 2px 2px " + toHex(ACCENT_SHADOW) + ";'>La Casa di Cenere</h2>"
            + "<div style='height: 10px;'></div>" 
            + "<span style='font-size: 17px;'>"
            + "Progetto realizzato per l'esame di Metodi Avanzati di Programmazione<br>"
            + "(Università A. Moro di Bari)."
            + "</span>"
            + "<span style='font-size: 20px; '></div>"
            + "<hr style='border-top: 1px solid " + toHex(COLD_LIGHT) + "; width: 60%;'>" 
            + "<div style='height: 20px;'></div>"
            + "<span style='font-size: 19px; font-weight: bold;'>Sviluppo, Design e Contenuti:</span>" 
            + "<span style='font-size: 24px; font-weight: bold;'>Raffaella Nitti<br>Aurora Marinelli</span>" // Font nomi leggermente ridotto      
            + "<div style='height: 10px;'></div>"
            + "<hr style='border-top: 1px solid " + toHex(COLD_LIGHT) + "; width: 60%;'>"
            + "<div style='height: 10px;'></div>"
            + "<span style='font-size: 18px;'>Grazie per aver giocato! 🙏</span>"   
            + "</center></html>"
        );
        
        contentLabel.setPreferredSize(new Dimension(600, 380)); 
        contentLabel.setBorder(BorderFactory.createLineBorder(COLD_LIGHT, 2)); 


        // --- LAYOUT (GroupLayout) ---
        GroupLayout backgroundPanelLayout = new GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(backgroundPanelLayout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addComponent(goBack, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(720, Short.MAX_VALUE))
                .addGroup(GroupLayout.Alignment.TRAILING, backgroundPanelLayout.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(backgroundPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
                        .addComponent(contentLabel, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15) 
                .addComponent(goBack, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15) 
                .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15) 
                .addComponent(contentLabel, GroupLayout.PREFERRED_SIZE, 380, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(backgroundPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(backgroundPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
    }
    
    /**
     * Converte un oggetto Color in una stringa esadecimale.
     * @param color
     * @return
     */
    private String toHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * Gestione dell'evento di click sul pulsante "goBack".
     * @param evt
     */
    private void goBackActionPerformed(ActionEvent evt) {
        CardLayout cl = (CardLayout) getParent().getLayout();
        cl.show(getParent(), "MenuGUI");
    }
}