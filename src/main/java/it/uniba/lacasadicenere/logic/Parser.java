/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.logic;

import it.uniba.lacasadicenere.entity.Command;
import it.uniba.lacasadicenere.entity.Game;
import it.uniba.lacasadicenere.entity.Item;
import it.uniba.lacasadicenere.entity.ItemContainer;
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

    private final Set<Command> availableCommands;
    private Set<Item> availableItems;
    private final GameManager gameManager;
    private final Set<String> stopwords = new HashSet<>();
    
    /**
     * Costruttore del Parser.
     * Carica i comandi disponibili e le stopwords.
     */
    public Parser() {
        gameManager = new GameManager();
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
                if (container.getContainedItems() != null) {
                    allItems.addAll(container.getContainedItems());
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
        System.out.println("=== DEBUG PARSER ===");
        System.out.println("Input ricevuto: '" + input + "'");
        
        updateAvailableItems();

        ParserOutput output = new ParserOutput();

        if (input == null || input.trim().isEmpty()) {
            System.out.println("NESSUN INPUT VALIDO. Ritorno output vuoto.");
            return output;
        }
        
        String[] words = Arrays.stream(input.split("\\s+"))
                .map(String::toLowerCase)
                .filter(word -> !word.isEmpty())
                .filter(word -> !stopwords.contains(word))
                .toArray(String[]::new);

        System.out.println("Parole dopo filtro: " + Arrays.toString(words));
        System.out.println("Numero di parole: " + words.length);

        if (words.length == 0) {
            System.out.println("NESSUNA PAROLA! Ritorno output vuoto.");
            return output;
        }
        
        for (Command command : availableCommands) {
            if (command.getName().equalsIgnoreCase(words[0]) ||
                    command.getAliases().stream().anyMatch(alias -> alias.equalsIgnoreCase(words[0]))) {
                output.setCommand(command.getType());
                output.setArgs(0);
                System.out.println("Comando trovato: " + command.getType());
                break;
            }
        }

        if (output.getCommand() == null) {
            System.out.println("COMANDO NON RICONOSCIUTO!");
            return output;
        }

        if(words.length > 1) {
            Item found = findObjectByName(words[1]);
            if(found != null) {
                output.setItem1(found);
                output.setArgs(1); 
                System.out.println("Item1 trovato: " + safeName(found));
            } else {
                // non trovato sul primo token: prova a cercare usando la frase rimanente
                String rest = String.join(" ", Arrays.copyOfRange(words, 1, words.length));
                Item foundRest = findObjectByName(rest);
                if (foundRest != null) {
                    output.setItem1(foundRest);
                    output.setArgs(1);
                    System.out.println("Item1 trovato (frase intera): " + safeName(foundRest));
                } else {
                    output.setArgs(0); // nessun oggetto riconosciuto
                    System.out.println("Item1 NON trovato per token: " + words[1]);
                }
            }
        } 

        if(words.length > 2) {
            Item found = findObjectByName(words[2]);
            if(found != null) {
                output.setItem2(found);
                output.setArgs(2);
                System.out.println("Item2 trovato: " + safeName(found));
            } else {
                // prova frase da parola 2 in poi
                String rest = String.join(" ", Arrays.copyOfRange(words, 2, words.length));
                Item foundRest = findObjectByName(rest);
                if (foundRest != null) {
                    output.setItem2(foundRest);
                    output.setArgs(2);
                    System.out.println("Item2 trovato (frase intera): " + safeName(foundRest));
                }
            }
        }
        
        System.out.println("Parser Output finale: comando=" + output.getCommand() + ", args=" + output.getArgs());
        System.out.println("====================");
    
        return output;
    }

    /**
     * Aggiorna la lista degli item disponibili includendo:
     * - item globali dal GameManager
     * - item nell'inventario
     * - item nella stanza corrente
     * - item contenuti nei container (anche annidati)
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
        
        System.out.println("=== updateAvailableItems: " + availableItems.size() + " items totali ===");
    }
    private void extractItemsFromContainers(Set<Item> target, Collection<Item> source) {
        for (Item item : source) {
            if (item instanceof ItemContainer) {
                ItemContainer container = (ItemContainer) item;
                if (container.getContainedItems() != null && !container.getContainedItems().isEmpty()) {
                    System.out.println("  Container: " + safeName(container) + " contiene " + container.getContainedItems().size() + " items");
                    for (Item contained : container.getContainedItems()) {
                        System.out.println("    - " + safeName(contained));
                        target.add(contained);
                    }
                    // ricorsione per container annidati
                    extractItemsFromContainers(target, container.getContainedItems());
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
            System.out.println("=== Cerco oggetto: nome nullo o vuoto ===");
            return null;
        }
        System.out.println("=== Cerco oggetto: '" + name + "' ===");
        System.out.println("Oggetti disponibili:");
        for(Item item : availableItems) {
            System.out.println("  - " + safeName(item) + " (alias: " + safeAliases(item) + ")");
        }
        String target = name.trim().toLowerCase();
        for(Item item : availableItems) {
            if (item == null) continue;
            String nm = item.getName() != null ? item.getName().toLowerCase() : "";
            if (!nm.isEmpty() && nm.equalsIgnoreCase(target)) {
                System.out.println("TROVATO: " + item.getName());
                return item;
            }
            if (item.getAliases() != null) {
                for (String alias : item.getAliases()) {
                    if (alias != null && alias.equalsIgnoreCase(target)) {
                        System.out.println("TROVATO per alias: " + item.getName());
                        return item;
                    }
                }
            }
        }
        System.out.println("NON TROVATO!");
        return null;
    }

    private String safeName(Item item) {
        if(item == null) return "[]";
        return item.getName() != null ? item.getName() : "unnamed";
    }

    private String safeAliases(Item item) {
        if(item == null) return "[]";
        return item.getAliases() != null ? item.getAliases().toString() : "[]";
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

        // fallback: prova a caricare dal classpath
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
        System.err.println("Parser: stopwords non trovate, usato set di default.");
    }
}