/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.type;

import it.uniba.lacasadicenere.entity.Item;

/**
 * Classe per rappresentare l'output del parser.
 */
public class ParserOutput {
    
    private CommandType command;
    
    private Item item;
    
    private int args;
    
    public ParserOutput() {
        args = 0;
    }
    
    public int getArgs() {
        return args;
    }
    
    public void setArgs(int args) {
        this.args = args;
    }
    
    public CommandType getCommand() {
        return command;
    }
    
    public void setCommand(CommandType command) {
        this.command = command;
    }
    
    public void setItem(Item item) {
        this.item = item;
    }
    
    public Item getItem() {
        return item;
    }
    
    
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof ParserOutput)) return false;
        ParserOutput that = (ParserOutput) o;
        return command == that.command &&
                (item != null ? item.equals(that.item) : that.item == null);
    }
                
    @Override
    public int hashCode() {
        int result = command != null ? command.hashCode() : 0;
        result = 31 * result + (item != null ? item.hashCode() : 0);
        return result;
    }    
}
