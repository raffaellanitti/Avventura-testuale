package it.uniba.lacasadicenere.gui;

import it.uniba.lacasadicenere.entity.Game;
import it.uniba.lacasadicenere.interactionManager.UserInputManager;
import it.uniba.lacasadicenere.logic.GameManager;

import javax.swing.Timer;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principale della GUI di gioco. Gestisce la visualizzazione della stanza,
 * dell'inventario, dell'output del testo e l'input dell'utente.
 */
public class GameGUI extends JPanel {


     // --- Componenti della ToolBar ---
    private JButton goBackButton;
    private JButton saveGameButton;
    private JButton helpButton;

    // --- Componenti di Visualizzazione ---
    private static JPanel imagePanel;
    private static JTextPane displayTextPane;
    private JScrollPane scrollPaneDisplayText;

    // --- Componenti di Inventario ---
    private static JTextArea inventoryTextArea;
    private JScrollPane scrollPaneInventoryText;

    // --- Componente di Input Utente ---
    private JTextField userInputField;
    private JToolBar toolBar;

    // Layout per il pannello delle immagini
    private static CardLayout cardLayout;

    /**
     * Costruttore della classe GameGUI.
     */
    public GameGUI() {
        UIManager.put("ScrollBar.width", 0);
        SwingUtilities.updateComponentTreeUI(this);
        initComponents();
        initImagePanel();
    }

    /**
     * Configura il pannello che ospita le immagini delle stanze usando CardLayout.
     */
    private void initImagePanel() {

        imagePanel.setPreferredSize(new Dimension(550, 400));
        imagePanel.setMaximumSize(new Dimension(550, 400));
        imagePanel.setMinimumSize(new Dimension(550, 400));
        imagePanel.setBorder(BorderFactory.createLineBorder(new Color(200, 220, 255), 5));
        imagePanel.setBackground(new Color(30, 30, 35, 255));

        cardLayout = new CardLayout();

        cardLayout.setVgap(0);
        cardLayout.setHgap(0);
        cardLayout.minimumLayoutSize(imagePanel);
        cardLayout.preferredLayoutSize(imagePanel);
        cardLayout.maximumLayoutSize(imagePanel);

        imagePanel.setLayout(cardLayout);

        // Carica le 12 immagini delle stanze e le aggiunge come "carte" al JPanel
        for(int i = 1; i <= 12; i++) {
            final String imagePath = "src/main/resources/img/Stanza" + i + ".jpg";
            imagePanel.add(new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    ImageIcon img = new ImageIcon(imagePath);
                    Image image = img.getImage();
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            }, "Stanza" + i);
        }
    }

    /**
     * Metodo per ottenere le metriche del font del pannello di testo.
     * @return FontMetrics
     */
    public static FontMetrics getTextPaneFontMetrics() {
        return displayTextPane.getFontMetrics(displayTextPane.getFont());
    }

    /**
     * Metodo per ottenere la larghezza del pannello di testo.
     * @return larghezza in pixel
     */
    public static int getTextPaneWidth() { 
        return displayTextPane.getWidth();
    }

    /**
     * Inizializza i componenti della GUI e definisce il layout.
     */
    private void initComponents() {
        
        // DEFINIZIONE COLORI
        final Color COLD_LIGHT = new Color(200, 220, 255, 255); 
        final Color COLD_SELECT_COLOR = new Color(100, 120, 140, 150); 
        final Color FOG_BACKGROUND = new Color(30, 30, 35, 255); 
        final Color DARK_FOG_CONTENT = new Color(45, 50, 55, 255); 

        setBackground(FOG_BACKGROUND);
        setPreferredSize(new Dimension(800, 600));

        // Inizializzazione componenti
        imagePanel = new JPanel();
        toolBar = new JToolBar();
        goBackButton = new JButton();
        saveGameButton = new JButton();
        helpButton = new JButton();
        displayTextPane = new JTextPane();
        scrollPaneDisplayText = new JScrollPane();
        inventoryTextArea = new JTextArea();
        scrollPaneInventoryText = new JScrollPane();
        userInputField = new JTextField();

        // --- TOOLBAR ---
        toolBar.setBorderPainted(false);
        toolBar.setFloatable(false);
        toolBar.setBackground(FOG_BACKGROUND);
        toolBar.add(Box.createHorizontalStrut(5));

        // --- GO BACK BUTTON ---
        goBackButton.setUI(new MetalButtonUI() {
            protected Color getSelectColor() {
                return COLD_SELECT_COLOR;
            }
        });
        goBackButton.setFocusPainted(false);
        goBackButton.setBackground(DARK_FOG_CONTENT);
        goBackButton.setForeground(COLD_LIGHT);
        goBackButton.setBorderPainted(true);
        goBackButton.setBorder(BorderFactory.createLineBorder(COLD_LIGHT, 2));
        goBackButton.setFont(new Font("Otacon", 1, 16));
        goBackButton.setText("<");
        goBackButton.setFocusable(false);
        goBackButton.setHorizontalTextPosition(SwingConstants.CENTER);
        goBackButton.setVerticalTextPosition(SwingConstants.CENTER);
        goBackButton.setOpaque(true);
        goBackButton.setContentAreaFilled(true);
        goBackButton.addActionListener(this::goBackButtonActionPerformed);
        toolBar.add(goBackButton);

        toolBar.add(Box.createHorizontalStrut(10));

        // --- SAVE GAME BUTTON ---
        saveGameButton.setUI(new MetalButtonUI() {
            protected Color getSelectColor () {
                return COLD_SELECT_COLOR;
            }
        });
        saveGameButton.setFocusPainted(false);
        saveGameButton.setBackground(DARK_FOG_CONTENT);
        saveGameButton.setForeground(COLD_LIGHT);
        saveGameButton.setBorderPainted(true);
        saveGameButton.setBorder(BorderFactory.createLineBorder(COLD_LIGHT, 2));
        saveGameButton.setFont(new Font("Otacon", 1, 16));
        saveGameButton.setText("Salva");
        saveGameButton.setToolTipText("");
        saveGameButton.setFocusable(false);
        saveGameButton.setHorizontalTextPosition(SwingConstants.CENTER);
        saveGameButton.setVerticalTextPosition(SwingConstants.CENTER);
        saveGameButton.setOpaque(true);
        saveGameButton.setContentAreaFilled(true);
        saveGameButton.addActionListener(evt -> {
            try {
                saveGameButtonActionPerformed(evt);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        toolBar.add(saveGameButton);

        toolBar.add(Box.createHorizontalStrut(10));

        // --- HELP BUTTON ---
        helpButton.setUI(new MetalButtonUI() {
            protected Color getSelectColor () {
                return COLD_SELECT_COLOR;
            }
        });
        helpButton.setFocusPainted(false);
        helpButton.setBackground(DARK_FOG_CONTENT);
        helpButton.setForeground(COLD_LIGHT);
        helpButton.setBorderPainted(true);
        helpButton.setBorder(BorderFactory.createLineBorder(COLD_LIGHT, 2));
        helpButton.setFont(new Font("Otacon", 1, 20));
        helpButton.setMargin(new Insets(0, 10, 0, 0));
        helpButton.setText("  ?  ");
        helpButton.setFocusable(false);
        helpButton.setHorizontalTextPosition(SwingConstants.CENTER);
        helpButton.setVerticalTextPosition(SwingConstants.CENTER);
        helpButton.setOpaque(true);
        helpButton.setContentAreaFilled(true);
        helpButton.addActionListener(this::helpButtonActionPerformed);
        toolBar.add(helpButton);

        toolBar.add(Box.createHorizontalStrut(10));

        // --- INVENTORY TEXT AREA ---
        inventoryTextArea.setEditable(false);
        inventoryTextArea.setOpaque(true);
        inventoryTextArea.setBackground(DARK_FOG_CONTENT);
        inventoryTextArea.setForeground(COLD_LIGHT);
        inventoryTextArea.setPreferredSize(new Dimension(440, 100));
        inventoryTextArea.setFont(new Font("Monospaced", 0, 16));
        inventoryTextArea.setText(" Inventario:\n");

        scrollPaneInventoryText.setViewportView(inventoryTextArea);
        scrollPaneInventoryText.setPreferredSize(new Dimension(200, 550));
        scrollPaneInventoryText.setBorder(BorderFactory.createMatteBorder(0, 5, 5, 5, COLD_LIGHT));

        // --- DISPLAY TEXT PANE ---
        displayTextPane.setEditable(false);
        displayTextPane.setFocusable(false);
        displayTextPane.setAutoscrolls(false);
        displayTextPane.setFont(new Font("Monospaced", 0, 15));
        displayTextPane.setBorder(null);
        displayTextPane.setOpaque(true);
        displayTextPane.setForeground(COLD_LIGHT);

        scrollPaneDisplayText.setBackground(FOG_BACKGROUND);
        scrollPaneDisplayText.setViewportView(displayTextPane);
        scrollPaneDisplayText.setPreferredSize(new Dimension(550, 120));
        scrollPaneDisplayText.setMinimumSize(new Dimension(550,120));
        scrollPaneDisplayText.setMaximumSize(new Dimension(550, 120));
        scrollPaneDisplayText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneDisplayText.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneDisplayText.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 0, COLD_LIGHT));

        // --- USER INPUT FIELD ---
        userInputField.addActionListener(this::userInputFieldActionPerformed);
        userInputField.setMargin(new Insets(0, 5, 0, 0));
        userInputField.setForeground(COLD_LIGHT);
        userInputField.setFont(new Font("Monospaced", 0, 15));
        userInputField.setOpaque(true);
        userInputField.setBackground(DARK_FOG_CONTENT);
        userInputField.setPreferredSize(new Dimension(335, 40));
        userInputField.setBorder(BorderFactory.createMatteBorder(0, 5, 5, 0, COLD_LIGHT));
        userInputField.setBounds(0, 0, 335, 40);
        UserInputManager.startInputListener();

        JPanel userInputFieldPanel = new JPanel();
        userInputFieldPanel.setLayout(null);
        userInputFieldPanel.setPreferredSize(new Dimension(500, 40));
        userInputFieldPanel.setBorder(null);
        userInputFieldPanel.add(userInputField);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(toolBar)
                .addGroup(layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(scrollPaneInventoryText, 200, 200, 200) 
                    .addGap(10, 10, 10)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(imagePanel, 550, 550, 550)
                        .addComponent(scrollPaneDisplayText, 550, 550, 550)
                        .addComponent(userInputFieldPanel, 550, 550, 550))
                    .addGap(10, 10, 10))
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                         .addGroup(layout.createSequentialGroup()
                            .addGap(10, 10, 10) 
                            .addComponent(scrollPaneInventoryText, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) 
                            .addGap(10, 10, 10)) 
                        .addGroup(layout.createSequentialGroup()
                            .addGap(10, 10, 10) 
                            .addComponent(imagePanel, 400, 400, 400) 
                            .addComponent(scrollPaneDisplayText, 120, 120, 120)
                            .addComponent(userInputFieldPanel, 40, 40, 40) 
                            .addGap(10, 10, 10)))
                )
        );
    }


    /**
     * Gestione dell'evento di click sul pulsante "Salva Partita".
     * @param evt
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void saveGameButtonActionPerformed(ActionEvent evt) throws IOException, ClassNotFoundException {
        Font font = new Font("Otacon", Font.PLAIN, 13);
        UIManager.put("OptionPane.messageFont", font);
        int save = JOptionPane.showConfirmDialog(this, "Would you like to save?", "Save", JOptionPane.YES_NO_OPTION);

        if (save == JOptionPane.YES_OPTION) {
            saveFile();
        } else {
            notSavedFile();
        }
    }

    /**
     * Salva il file di gioco.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void saveFile() throws IOException, ClassNotFoundException {
        GameManager gameManager = new GameManager();
        Game game = Game.getInstance();
        
        game.setCurrentTime("00:00:00");
        gameManager.saveGame();
        JOptionPane.showMessageDialog(this, "Game saved successfully", "Save", JOptionPane.INFORMATION_MESSAGE);
        goBack();
    }

    /**
     * Notifica che il file di gioco non è stato salvato.
     */
    private void notSavedFile() {
        UIManager.put("OptionPane.messageFont", new Font("Papyrus", 0, 13));
        JOptionPane.showMessageDialog(this, "Game not saved", "Save", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Gestione dell'evento di click sul pulsante "Torna Indietro".
     * @param evt
     */
    private void goBackButtonActionPerformed(ActionEvent evt) {
        UIManager.put("OptionPane.messageFont", new Font("Otacon", 0, 13));
        int back = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler tornare al Menù senza salvare?", "Back", JOptionPane.YES_NO_OPTION);

        if (back == JOptionPane.YES_OPTION) {
            goBack();
        } else {
            notGoBack();
        }
    }

    /**
     * Torna al Menu principale della GUI di gioco.
     */
    public void goBack() {
        CardLayout cl = (CardLayout) getParent().getLayout();
        cl.show(getParent(), "MenuGUI");
        displayTextPane.setText("");
        inventoryTextArea.setText(" Inventory:\n");
    }

    /**
     * Notifica che l'utente non è tornato indietro.
     */
    private void notGoBack() {
        UIManager.put("OptionPane.messageFont", new Font("Otacon", 0, 13));
        JOptionPane.showMessageDialog(this, "Sii più deciso la prossima volta", "Back", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Gestione dell'evento di click sul pulsante "Help".
     * @param evt
     */
    private void helpButtonActionPerformed(ActionEvent evt) {
        HelpGUI helpGUI = HelpGUI.getInstance();
        helpGUI.setVisible(true);
    }

    /**
     * Gestione dell'evento di invio del testo nel campo di input utente.
     * @param evt
     */
    private void userInputFieldActionPerformed(ActionEvent evt) {
        String text = userInputField.getText();
        userInputField.setText("");
        UserInputManager.setUserInput(text);
    }

    /**
     * Imposta il testo nel pannello di visualizzazione del testo.
     * @param text
     */
    public static void displayTextPaneSetText(String text) {
        if (displayTextPane.getText().isEmpty()) {
            displayTextPane.setText(text);
        } else {
            displayTextPane.setText(displayTextPane.getText() + "\n" + text);
        }
        displayTextPane.setCaretPosition(displayTextPane.getDocument().getLength());
    }
    
    /**
     * Aggiunge del testo al pannello di visualizzazione del testo.
     * @param text
     */
    public static void displayTextPaneAppendText(String text) {
        SwingUtilities.invokeLater(() -> {
            try {
                javax.swing.text.Document doc = displayTextPane.getDocument();
                doc.insertString(doc.getLength(), text, null);
                displayTextPane.setCaretPosition(doc.getLength());
            } catch (javax.swing.text.BadLocationException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Imposta l'immagine della stanza corrente nel pannello delle immagini con un ritardo visivo.
     * @param panelName
     */
    public static void setImagePanel(String panelName) {
        Timer timerImagePanel = new Timer(600, _e -> cardLayout.show(imagePanel, panelName));
        timerImagePanel.setRepeats(false);
        timerImagePanel.start();
    }

    /**
     * Aggiorna il pannello di testo dell'inventario con gli oggetti attuali.
     * @param items
     */
    public static void updateInventoryTextArea(String[] items) {
        StringBuilder inventory = new StringBuilder(" Inventario:\n");
        int maxHorItems = 3;

        int i = 0;
        while (i < items.length) {
            int j = 0;
            while (j < maxHorItems && i < items.length) {
                inventory.append(" - ").append(items[i]).append("   ");
                j++;
                i++;
            }
            inventory.append("\n");
        }

        inventoryTextArea.setText(inventory.toString());
    }

    /**
     * Ottiene il pannello di testo dell'inventario.
     * @return
     */
    public static JTextArea getInventoryTextArea() {
        return inventoryTextArea;
    }

    /**
     * Ottiene i nomi degli oggetti nell'inventario come array di stringhe.
     * @return
     */
    public static String[] getInventoryItemNames() {
        String text = inventoryTextArea.getText();
        String[] lines = text.split("\n");
        List<String> itemNames = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            String name = lines[i].replace(" - ", "").trim();
            if (!name.isEmpty()) {
                itemNames.add(name);
            }
        }
        return itemNames.toArray(new String[0]);
    }
}

