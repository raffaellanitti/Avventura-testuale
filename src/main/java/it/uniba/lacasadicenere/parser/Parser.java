/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.parser;

import it.uniba.lacasadicenere.entity.GameObject;
import it.uniba.lacasadicenere.entity.GameCommand;

import java.util.List;
import java.util.Set;

/**
 * Classe che rappresenta il parser
 */
public class Parser {
    
    private final Set<String> stopwords;

    /**
     *
     * @param stopwords
     */
    public Parser(Set<String> stopwords) {
        this.stopwords = stopwords;
    }

    private int checkForCommand(String token, List<GameCommand> commands) {
        for (int i = 0; i < commands.size(); i++) {
            if (commands.get(i).getCommandName().equals(token) || commands.get(i).getAliases().contains(token)) {
                return i;
            }
        }
        return -1;
    }

    private int checkForObject(String token, List<GameObject> obejcts) {
        for (int i = 0; i < obejcts.size(); i++) {
            GameObject object = obejcts.get(i);
            if (object.getName().equalsIgnoreCase(token) || object.getAliases().contains(token.toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    /**
     *
     * @param input
     * @param commands
     * @param objects
     * @return ParserOutput
     */

    public ParserOutput parse(String input, List<GameCommand> commands, List<GameObject> objects) {
            // Suddivide la stringa in token e rimuove le stopwords
        List<String> tokens = ParserUtils.parseString(input, stopwords);
        if (tokens.isEmpty()) {
            return null; // nessun comando
        }

        // Controlla il comando (primo token)
        int ic = checkForCommand(tokens.get(0), commands);
        if (ic < 0) {
            return new ParserOutput(null, null); // comando non trovato
        }

        GameCommand cmd = commands.get(ic);
        GameObject obj = null;

        // Se ci sono altri token, cerca un oggetto corrispondente
        if (tokens.size() > 1) {
            for (int i = 1; i < tokens.size(); i++) {
                int io = checkForObject(tokens.get(i), objects);
                if (io >= 0) {
                    obj = objects.get(io);
                    break;
                }
            }  
        }

        return new ParserOutput(cmd, obj);
    }
}
