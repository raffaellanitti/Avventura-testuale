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
    
    private Item item1;
    private Item item2;
    
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
    
    public void setItem1(Item item1) {
        this.item1 = item1;
    }
    
    public Item getItem1() {
        return item1;
    }

    public void setItem2(Item item2) {
        this.item2 = item2;
    }
    
    public Item getItem2() {
        return item2;
    }
    
    
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof ParserOutput)) return false;
        ParserOutput that = (ParserOutput) o;
        return command == that.command &&
                (item1 != null ? item1.equals(that.item1) : that.item1 == null) &&
                (item2 != null ? item2.equals(that.item2) : that.item2 == null);
    }
                
    @Override
    public int hashCode() {
        int result = command != null ? command.hashCode() : 0;
        result = 31 * result + (item1 != null ? item1.hashCode() : 0);
        result = 31 * result + (item2 != null ? item2.hashCode() : 0);
        return result;
    }    
}
