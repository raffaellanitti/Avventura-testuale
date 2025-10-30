/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

/**
 * Finestra di aiuto che mostra i comandi di gioco disponibili.
 */
public class HelpDialog extends JFrame {
    
    /**
     * Istanza singleton di HelpDialog.
     */
    private static HelpDialog instance;
    
    /**
     * Costruttore privato per il pattern singleton.
     */
    HelpDialog() {
        initComponents();
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
    
    /**
     * Restituisce l'istanza singleton di HelpDialog.
     * @return
     */
    public static HelpDialog getInstance() {
        if(instance == null) {
            instance = new HelpDialog();
        }
        return instance;
    }
    
    /**
     * Inizializza i componenti grafici della finestra di aiuto.
     */
    private void initComponents() {
        
        // DEFINIZIONE COLORI 
        final Color COLD_LIGHT = new Color(200, 220, 255); 
        final Color FOG_BACKGROUND = new Color(30, 30, 35); 
        final Color DARK_FOG_CONTENT = new Color(45, 50, 55);
        
        JLabel listaComandi = new JLabel();
        
        setTitle("Comandi di Gioco");
        setPreferredSize(new Dimension(460, 780));
        setMaximumSize(new Dimension(460, 780));
        setMinimumSize(new Dimension(460, 780));
        setResizable(false);
        getContentPane().setBackground(FOG_BACKGROUND);
        
        Font font = new Font("Monospaced", Font.PLAIN, 16);
        listaComandi.setOpaque(true);
        listaComandi.setBackground(DARK_FOG_CONTENT);
        listaComandi.setForeground(COLD_LIGHT);
        listaComandi.setFont(font);
        listaComandi.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, COLD_LIGHT));
        listaComandi.setText(
            "<html>" +
                "<center>" +
                    "<p><b><font color='#FFFFFF'>Lista Comandi di Gioco</font></b></p>" +
                    "<p><b>Nord</b> - Spostarsi a nord</p>" +
                    "<p><b>Sud</b> - Spostarsi a sud</p>" +
                    "<p><b>Est</b> - Spostarsi a est</p>" +
                    "<p><b>Ovest</b> - Spostarsi a ovest</p>" +
                    "<p><b>Inventario</b> - Visualizzare l'inventario</p>" +
                    "<p><b>Osserva</b> - Mostra l'ambiente circostante</p>" +
                    "<p><b>Osserva</b> [<i>nome oggetto</i>] - Mostra la descrizione di un oggetto</p>" +
                    "<p><b>Prendi</b> [<i>nome oggetto</i>] - Prendere un oggetto</p>" +
                    "<p><b>Usa</b> [<i>nome oggetto</i>] - Usare un oggetto</p>" +
                    "<p><b>Lascia</b> [<i>nome oggetto</i>] - Lasciare un oggetto</p>" +
                "</center>" +
            "</html>"
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(listaComandi, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(listaComandi, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }
}