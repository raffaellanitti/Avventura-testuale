/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.logic;

import it.uniba.lacasadicenere.entity.Command;
import it.uniba.lacasadicenere.entity.Item;
import it.uniba.lacasadicenere.type.ParserOutput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe che analizza l'input dell'utente e lo converte in comandi di gioco.
 */
public class Parser {

    private final Set<Command> availableCommands;
    private final Set<Item> availableItems;
    private final GameManager gameManager;
    private final Set<String> stopwords = new HashSet<>();
    
    /**
     * Costruttore del Parser.
     * Carica i comandi disponibili e le stopwords.
     */
    public Parser() {
        gameManager = new GameManager();
        availableCommands = gameManager.getAllCommands();
        availableItems = gameManager.getItems();
        
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
        ParserOutput output = new ParserOutput();
        
        String[] words = Arrays.stream(input.trim().split("\\s+")) 
            .map(String::toLowerCase)                             
            .filter(word -> !stopwords.contains(word) && !word.isEmpty()) 
            .toArray(String[]::new);
        
        if (words.length == 0) {
            return output;
        }
        
        
        for (Command command : availableCommands) {
            if (command.getName().equalsIgnoreCase(words[0]) ||
                    command.getAliases().stream().anyMatch(alias -> alias.equalsIgnoreCase(words[0]))) {
                output.setCommand(command.getType());
                break;
            }
        }

        if (output.getCommand() == null) {
            return output;
        }

        if(words.length > 1) {
            Item found = findObjectByName(words[1]);
            if(found != null) {
                output.setItem(found);
                output.setArgs(1); 
            } else {
                output.setArgs(0);
            }
        } else {
            output.setArgs(0);
        }
        return output;
    }
    /**
     * Cerca un oggetto per nome o alias tra tutti gli oggetti disponibili.
     * 
     * @param name Il nome o alias dell'oggetto da cercare
     * @return L'oggetto trovato, o null se non esiste
     */
    private Item findObjectByName(String name) {
        for(Item item : availableItems) {
            if(item.getName().equalsIgnoreCase(name) ||
                    item.getAliases().stream().anyMatch(alias -> alias.equalsIgnoreCase(name))) {
                return item;
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
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = reader.readLine()) != null) {
                stopwords.add(line.trim().toLowerCase());
            }
        }
    }
}