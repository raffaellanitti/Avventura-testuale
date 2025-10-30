/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.type;

/**
 * Chiave per la HashMap dei comandi nel CommandExecutor.
 * Combina il tipo di comando con il numero di argomenti.
 */
public class CommandKey {
    
    private CommandType command;
    private int args;
    
    public CommandKey(CommandType c, int a) {
        this.command = c;
        this.args = a;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandKey)) return false;
        CommandKey that = (CommandKey) o;
        return command == that.command && args == that.args;
    }

    @Override
    public int hashCode() {
        int result = command != null ? command.hashCode() : 0;
        result = 31 * result + args;
        return result;
    }
}