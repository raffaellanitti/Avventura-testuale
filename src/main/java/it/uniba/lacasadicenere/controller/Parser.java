/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.controller;

import it.uniba.lacasadicenere.model.Command;
import it.uniba.lacasadicenere.model.Game;
import it.uniba.lacasadicenere.model.Item;
import it.uniba.lacasadicenere.model.ItemContainer;
import it.uniba.lacasadicenere.type.ParserOutput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe che analizza l'input dell'utente e lo converte in comandi di gioco.
 */
public class Parser {

    /**
     * Insieme dei comandi di gioco disponibili.
     */
    private final Set<Command> availableCommands;

    /**
     * Insieme degli oggetti di gioco disponibili.
     */
    private Set<Item> availableItems;

    /**
     * Riferimento all'istanza del GameController.
     */
    private final GameController gameManager;

    /**
     * Insieme delle parole inutili (stopwords) da ignorare durante il parsing.
     */
    private final Set<String> stopwords = new HashSet<>();
    
    /**
     * Costruttore del Parser.
     * Carica i comandi disponibili e le stopwords.
     */
    public Parser() {
        gameManager = new GameController();
        availableCommands = gameManager.getAllCommands();

        availableItems = new HashSet<>();
        if(gameManager.getItems() != null) {
        availableItems.addAll(gameManager.getItems());
        }
        Game game = Game.getInstance();
        if (game != null && game.getInventory() != null) {
            availableItems.addAll(game.getInventory());
        }
        
        Set<Item> allItems = new HashSet<>(availableItems);
        for (Item item : availableItems) {
            if (item instanceof ItemContainer) {
                ItemContainer container = (ItemContainer) item;
                if (container.getList() != null) {  
                    allItems.addAll(container.getList());
                }
            }
        }
        availableItems = allItems;
    
        try {
            setUpUselessWords();
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il caricamento delle stopwords", e);
        }
    }

    /**
     * Analizza l'input dell'utente e restituisce un ParserOutput.
     * 
     * @param input Il testo inserito dall'utente
     * @return ParserOutput contenente comando e oggetto (se presente)
     */
    public ParserOutput parse(String input) {
        
        updateAvailableItems();

        ParserOutput output = new ParserOutput();

        if (input == null || input.trim().isEmpty()) {
            return output;
        }
        
        String[] words = Arrays.stream(input.split("\\s+"))
                .map(String::toLowerCase)
                .filter(word -> !word.isEmpty())
                .filter(word -> !stopwords.contains(word))
                .toArray(String[]::new);

        if (words.length == 0) {
            return output;
        }
        
        for (Command command : availableCommands) {
            if (command.getName().equalsIgnoreCase(words[0]) ||
                    command.getAliases().stream().anyMatch(alias -> alias.equalsIgnoreCase(words[0]))) {
                output.setCommand(command.getType());
                output.setArgs(0);
                break;
            }
        }

        if (output.getCommand() == null) {
            return output;
        }

        if(words.length > 1) {
            Item found = findObjectByName(words[1]);
            if(found != null) {
                output.setItem1(found);
                output.setArgs(1); 
            } else {
                String rest = String.join(" ", Arrays.copyOfRange(words, 1, words.length));
                Item foundRest = findObjectByName(rest);
                if (foundRest != null) {
                    output.setItem1(foundRest);
                    output.setArgs(1);
                } else {
                    output.setArgs(0);
                }
            }
        } 

        if(words.length > 2) {
            Item found = findObjectByName(words[2]);
            if(found != null) {
                output.setItem2(found);
                output.setArgs(2);
            } else {
                String rest = String.join(" ", Arrays.copyOfRange(words, 2, words.length));
                Item foundRest = findObjectByName(rest);
                if (foundRest != null) {
                    output.setItem2(foundRest);
                    output.setArgs(2);
                }
            }
        }
    
        return output;
    }

    /**
     * Aggiorna l'insieme degli oggetti disponibili per il parsing.
     */
    private void updateAvailableItems() {
        availableItems = new HashSet<>();
        
        if (gameManager.getItems() != null) {
            availableItems.addAll(gameManager.getItems());
        }
        
        Game game = Game.getInstance();
        if (game != null) {
            if (game.getInventory() != null) {
                availableItems.addAll(game.getInventory());
            }
            
            if (game.getCurrentRoom() != null && game.getCurrentRoom().getItems() != null) {
                availableItems.addAll(game.getCurrentRoom().getItems());
            }
        }
        
        Set<Item> allItems = new HashSet<>(availableItems);
        extractItemsFromContainers(allItems, availableItems);
        availableItems = allItems;
    }

    /**
     * Metodo ricorsivo per estrarre gli oggetti contenuti nei container.
     * @param target
     * @param source
     */
    private void extractItemsFromContainers(Set<Item> target, Collection<Item> source) {
    for (Item item : source) {
        if (item instanceof ItemContainer) {
            ItemContainer container = (ItemContainer) item;
            if (container.getList() != null && !container.getList().isEmpty()) {
                target.addAll(container.getList());
                extractItemsFromContainers(target, container.getList());
            }
        }
    }
}

    /**
     * Cerca un oggetto per nome o alias tra tutti gli oggetti disponibili.
     * 
     * @param name Il nome o alias dell'oggetto da cercare
     * @return L'oggetto trovato, o null se non esiste
     */
     private Item findObjectByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        
        String target = name.trim().toLowerCase();
        for(Item item : availableItems) {
            if (item == null) continue;
            
            String nm = item.getName() != null ? item.getName().toLowerCase() : "";
            if (!nm.isEmpty() && nm.equalsIgnoreCase(target)) {
                return item;
            }
            
            if (item.getAliases() != null) {
                for (String alias : item.getAliases()) {
                    if (alias != null && alias.equalsIgnoreCase(target)) {
                        return item;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Carica le stopwords dal file nel classpath.
     * Le stopwords sono parole che vengono ignorate durante il parsing (es. "il", "la", "un").
     * 
     * @throws IOException Se il file non può essere letto
     */
    private void setUpUselessWords() throws IOException {
        File file = new File("src/main/resources/stopwords.txt");
        if (file.exists()) {
            try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) stopwords.add(line.trim().toLowerCase());
                }
            }
            return;
        }

        try (var in = Parser.class.getResourceAsStream("/stopwords.txt")) {
            if (in != null) {
                try (BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(in))) {
                    String line;
                    while((line = reader.readLine()) != null) {
                        if (!line.trim().isEmpty()) stopwords.add(line.trim().toLowerCase());
                    }
                }
                return;
            }
        }
    }
}