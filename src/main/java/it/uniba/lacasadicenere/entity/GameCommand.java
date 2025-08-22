/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.entity;

import java.util.List;

/**
 * Classe che rappresenta un comando nel gioco
 * Ogni comando ha un nome principale, eventuali alias e un tipo
 */
public class GameCommand {
    
    /** Nome del comando */
    private final String commandName;
    
    /** Lista degli alias con cui il comando può essere chiamato */
    private final List<String> aliases;
    
    /** Tipo del comando */
    private final CommandType type;
    
     /**
     * Costruisce un comando con nome, sinonimi e tipo specificati.
     *
     * @param commandName nome principale del comando
     * @param aliases lista di sinonimi del comando
     * @param type tipo del comando
     */
    public GameCommand(String commandName, List<String> aliases, CommandType type) {
        this.commandName = commandName;
        this.aliases = aliases;
        this.type = type;
    }
    
    // ==== Getter ====
    
     /**
     * Restituisce il nome del comando.
     *
     * @return nome del comando
     */
    public String getCommandName() {
        return this.commandName;
    }
    
    /**
     * Restituisce la lista di alias del comando.
     *
     * @return lista di alias
     */
    public List<String> getAliases() {
        return this.aliases;
    }
    
    /**
     * Restituisce il tipo del comando.
     *
     * @return tipo del comando
     */
    public CommandType getType() {
        return this.type;
    }
}
