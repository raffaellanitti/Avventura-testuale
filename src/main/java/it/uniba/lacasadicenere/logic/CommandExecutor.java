/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.logic;

import it.uniba.lacasadicenere.database.DatabaseConnection;
import it.uniba.lacasadicenere.entity.Item;
import it.uniba.lacasadicenere.entity.ItemContainer;
import it.uniba.lacasadicenere.entity.Game;
import it.uniba.lacasadicenere.entity.Corridor;
import it.uniba.lacasadicenere.type.CommandType;
import it.uniba.lacasadicenere.type.CommandExecutorKey; 
import it.uniba.lacasadicenere.type.CommandBehavior;
import it.uniba.lacasadicenere.type.ParserOutput;
import it.uniba.lacasadicenere.interactionManager.OutputDisplayManager;
import it.uniba.lacasadicenere.interactionManager.UserInputFlow;
import it.uniba.lacasadicenere.interactionManager.MirrorGame;

import java.util.HashMap;

/**
 * Esegue i comandi del gioco in base all'input parsato dall'utente.
 */
public class CommandExecutor {
    
    private Game game;
    private final HashMap<CommandExecutorKey, CommandBehavior> commandMap;
    private GameLogic gameLogic;

    /**
     * Crea un comportamento per i comandi di movimento direzionale.
     */
    private CommandBehavior createDirectionCommandBehavior(CommandType direction) {
        return p -> {
            Corridor corridor = game.getCorridorMap().stream()
                    .filter(c -> c.getStartingRoom().getName().equals(game.getCurrentRoom().getName()) 
                              && c.getDirection() == direction)
                    .findFirst()
                    .orElse(null);
            
            if(corridor != null && !corridor.isLocked()) {
                game.setCurrentRoom(corridor.getArrivingRoom());
            } else if (corridor != null && corridor.isLocked()) {
                OutputDisplayManager.displayText("Il corridoio verso " + direction + " è bloccato!");
            } else {
                OutputDisplayManager.displayText("Non c'è un passaggio verso " + direction + "!");
            }
        };  
    }

    public CommandExecutor(Game game) {
        this.game = game;
        this.gameLogic = new GameLogic(game);
        commandMap = new HashMap<>();

        commandMap.put(new CommandExecutorKey(CommandType.NORD, 0),
                createDirectionCommandBehavior(CommandType.NORD));
        commandMap.put(new CommandExecutorKey(CommandType.SUD, 0),
                createDirectionCommandBehavior(CommandType.SUD));
        commandMap.put(new CommandExecutorKey(CommandType.EST, 0),
                createDirectionCommandBehavior(CommandType.EST));
        commandMap.put(new CommandExecutorKey(CommandType.OVEST, 0),
                createDirectionCommandBehavior(CommandType.OVEST));
        
        commandMap.put(new CommandExecutorKey(CommandType.OSSERVA, 0),
                p -> game.getCurrentRoom().printDescription());

        commandMap.put(new CommandExecutorKey(CommandType.INVENTARIO, 0),
                p -> game.printInventory());
        
        commandMap.put(new CommandExecutorKey(CommandType.OSSERVA, 1),
                p -> {
                    Item item = p.getItem();
                    
                    if (item == null) {
                        OutputDisplayManager.displayText("Oggetto non specificato.");
                        return;
                    }
                    if(game.getCurrentRoom().getItems().contains(item)) {
                        DatabaseConnection.printFromDB("Osserva", game.getCurrentRoom().getName(), String.valueOf(game.getCurrentRoom()), item.getName());
                    } else if(game.getInventory().contains(item)) {
                        OutputDisplayManager.displayText("La tua borsa non è trasparente!");
                    } else {
                        OutputDisplayManager.displayText(item.getName() + " non è nella stanza!");
                    }
                });
        
        commandMap.put(new CommandExecutorKey(CommandType.PRENDI, 1),
                p -> {
                    Item item = p.getItem();

                    if (item == null) {
                        OutputDisplayManager.displayText("Devi specificare un oggetto da prendere.");
                        return;
                    }
                    
                    if (game.getInventory().contains(item)) {
                        OutputDisplayManager.displayText("Hai già " + item.getName() + " nell'inventario.");
                    } else if (game.getCurrentRoom().getItems().contains(item)) {
                        if (item.isPickable()) {
                            game.addInventory(item);
                            game.getCurrentRoom().removeItem(item.getName());
                            OutputDisplayManager.displayText("Hai preso " + item.getName() + ".");
                        } else {
                            OutputDisplayManager.displayText(item.getName() + " non può essere preso.");
                        }
                    } else {
                        OutputDisplayManager.displayText(item.getName() + " non è presente nella stanza.");
                    }
                });

        commandMap.put(new CommandExecutorKey(CommandType.USA, 1),
                p -> {
                    Item item = p.getItem();
                    
                    if(game.getInventory().contains(item)) {
                        boolean used = gameLogic.executeUseSingeItem(item);
                        if(!used) {
                            OutputDisplayManager.displayText("Non succede nulla usando " + item.getName() + ".");
                            return;
                        } 
                        DatabaseConnection.printFromDB("Usa", game.getCurrentRoom().getName(), String.valueOf(game.getCurrentRoom()), item.getName());
                    } else {
                        OutputDisplayManager.displayText("Non puoi usare qualcosa che non possiedi!");
                    }
                });
        
        commandMap.put(new CommandExecutorKey(CommandType.LASCIA, 1), p -> {
            Item item = p.getItem();

            if (item == null) {
                OutputDisplayManager.displayText("Devi specificare un oggetto da lasciare.");
                return;
            }

            if (!game.getInventory().contains(item)) {
                OutputDisplayManager.displayText("Non possiedi " + item.getName() + ".");
                return;
            }

            game.removeInventory(item);
            game.getCurrentRoom().addItems(item);
            OutputDisplayManager.displayText("Hai lasciato " + item.getName() + " nella stanza.");
        });

    }
    /**
     * Esegue il comando appropriato in base all'output del parser.
     * 
     * @param p L'output del parser contenente comando e oggetto
     */
    public void execute(ParserOutput p) {
        if (p == null || p.getCommand() == null) {
            OutputDisplayManager.displayText("Non ho capito il comando.");
            return;
        }
        int args = p.getArgs();
        
        CommandExecutorKey key = new CommandExecutorKey(p.getCommand(), args);
        CommandBehavior behavior = commandMap.get(key);
        
        if(behavior != null) {
            behavior.execute(p);
        } else {
            OutputDisplayManager.displayText("Comando non valido o argomenti errati.");
        }
    }
}