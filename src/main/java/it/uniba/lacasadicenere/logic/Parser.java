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
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

/**
 * Classe che rappresenta il parser del gioco.
 */
public class Parser {

    private final Set<Command> availableCommands;
    GameManager gameManager = new GameManager(); 
    private final Set<String> stopWords = new HashSet<>();
    
    /*
     * 
     */
    public Parser() {
        gameManager = new GameManager();
        availableCommands = gameManager.getAllCommands();

        try {
            setupUselessWords();
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il caricamento delle stopwords",e);
        }
    }

    /**
     * 
     * @param input
     * @return 
     */
    public ParserOutput parse(String input) {
        ParserOutput output = new ParserOutput();
        
        String[] words = Arrays.stream(input.split(" "))
                .map(String::toLowerCase)
                .filter(word -> !stopWords.contains(word))
                .toArray(String[]::new);
        
        if (words.length == 0) {
            return output;
        }
        
        for (Command command : availableCommands) {
            if (command.getName().equalsIgnoreCase(words[0]) ||
                    command.getAliases().stream().anyMatch(alias -> alias.equalsIgnoreCase(words[0]))) {
                output.setCommand(command.getType());
                output.setArgs(1);
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
                output.setArgs(2);
            } else {
                output.setArgs(1);
            }
        }
        return output;
    }

    /**
     * 
     * @param name
     * @return 
     */
    private Item findObjectByName(String name) {
        
        Set<Item> availableItems = gameManager.getItems();
        
        for (Item item : availableItems) {
            if (item.hasName(name)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 
     * @throws IOException 
     */
    public void setupUselessWords() throws IOException {
        File file = new File("src/main/resources/stopwords.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                stopWords.add(line.trim().toLowerCase());
            }
        }
    }
}
