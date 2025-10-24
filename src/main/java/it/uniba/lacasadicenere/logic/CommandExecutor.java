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

import java.util.HashMap;

/**
 * */
public class CommandExecutor {
    private Game game;
    private final HashMap<CommandExecutorKey, CommandBehavior> commandMap;
    private GameLogic gameLogic;

    private CommandBehavior createDirectionCommandBehavior(CommandType direction) {
        return p -> {
            Corridor corridor = game.getCorridorMap().stream()
                    .filter(c -> c.getStartingRoom().getName().equals(game.getCurrentRoom().getName()) && c.getDirection() == direction)
                    .findFirst()
                    .orElse(null);
            
            if(corridor != null && !corridor.isLocked()) {
                game.setCurrentRoom(corridor.getArrivingRoom());
                DatabaseConnection.printFromDB("Osserva", game.getCurrentRoom().getName(), "true", "0");
            } else if (corridor != null && corridor.isLocked()) {
                OutputDisplayManager.displayText("Il corridoio verso " + direction + " è bloccato.");
            } else {
                OutputDisplayManager.displayText("Non c'è un corridoio verso " + direction + ".");
            }
        };  
    }

    public CommandExecutor(Game game) {
        this.game = game;
        this.gameLogic = new GameLogic(game);
        commandMap = new HashMap<>();

        // Direzioni
        commandMap.put(new CommandExecutorKey(CommandType.NORD, 1),
                createDirectionCommandBehavior(CommandType.NORD));
        commandMap.put(new CommandExecutorKey(CommandType.SUD, 1),
                createDirectionCommandBehavior(CommandType.SUD));
        commandMap.put(new CommandExecutorKey(CommandType.EST, 1),
                createDirectionCommandBehavior(CommandType.EST));
        commandMap.put(new CommandExecutorKey(CommandType.OVEST, 1),
                createDirectionCommandBehavior(CommandType.OVEST));
        
        // Osserva stanza
        commandMap.put(new CommandExecutorKey(CommandType.OSSERVA, 1),
                p -> game.getCurrentRoom().printDescription());

        // Inventario
        commandMap.put(new CommandExecutorKey(CommandType.INVENTARIO, 1),
                p -> game.printInventory());
        
        // Messaggio di errore per prendi
        commandMap.put(new CommandExecutorKey(CommandType.PRENDI, 1),
                p -> OutputDisplayManager.displayText("Devi specificare un oggetto da prendere."));

        // Messaggio di errore per usa
        commandMap.put(new CommandExecutorKey(CommandType.USA, 1),
                p -> OutputDisplayManager.displayText("Devi specificare un oggetto da usare."));

        // Messaggio di errore per lascia
        commandMap.put(new CommandExecutorKey(CommandType.LASCIA, 1),
                p -> OutputDisplayManager.displayText("Devi specificare un oggetto da lasciare."));

        // Prendi oggetto
        commandMap.put(new CommandExecutorKey(CommandType.PRENDI, 2),
                p -> {
                    Item item = p.getItem();
                    if(game.getInventory().contains(item)) {
                        OutputDisplayManager.displayText("Hai già " + item.getName() + " nell'inventario.");
                    } else if (game.getCurrentRoom().getItems().contains(item)) {
                        if(p.getItem().isPickable()) {
                            game.addInventory(item);
                            game.getCurrentRoom().removeItem(item.getName());
                            OutputDisplayManager.displayText("Hai preso " + item.getName() + ".");
                        } else {
                            OutputDisplayManager.displayText(item.getName() + " non può essere preso.");
                        }
                    } else {
                        ItemContainer parentContainer = game.getCurrentRoom().getItems().stream()
                                .filter(i -> i instanceof ItemContainer)
                                .map(i -> (ItemContainer) i)
                                .filter(c -> c.getContainedItems().contains(item))
                                .findFirst()
                                .orElse(null);

                        if (parentContainer != null) {
                            if (item.isPickable()) {
                                parentContainer.removeContainedItem(item); 
                                game.addInventory(item); 
                                OutputDisplayManager.displayText("Hai preso " + item.getName() + " da " + parentContainer.getName() + ".");
                    
                                gameLogic.executePostPickupEffects(item, parentContainer);
                                
                            } else {
                                OutputDisplayManager.displayText(item.getName() + " non può essere preso (anche se è in un contenitore).");
                            }
                        } else {
                            OutputDisplayManager.displayText(item.getName() + " non è presente nella stanza.");
                        }
                    }
                }
        );

        // Osserva oggetto
        commandMap.put(new CommandExecutorKey(CommandType.OSSERVA, 2),
                p -> {
                    Item item = p.getItem();
                    
                    if(game.getCurrentRoom().getItems().contains(item) || game.getInventory().contains(item)) {
                        DatabaseConnection.printFromDB("Osserva", game.getCurrentRoom().getName(), "true", item.getName());
                    } else {
                        boolean found = game.getCurrentRoom().getItems().stream()
                            .filter(i -> i instanceof ItemContainer)
                            .map(i -> (ItemContainer) i)
                            .anyMatch(c -> c.getContainedItems().contains(item));
            
                        if(found) {
                            DatabaseConnection.printFromDB("Osserva", game.getCurrentRoom().getName(), "true", item.getName());
                        } else {
                            OutputDisplayManager.displayText(item.getName() + " non è presente nella stanza.");
                        }
                    }
            });
        
        commandMap.put(new CommandExecutorKey(CommandType.USA, 2),
                p -> {
                    Item item = p.getItem();
                    
                    if(game.getInventory().contains(p.getItem())) {
                        boolean used = gameLogic.executeUseSingeItem(p.getItem());
                        if(!used) {
                            OutputDisplayManager.displayText("Non succede nulla usando " + p.getItem().getName() + ".");
                            return;
                        } else {
                            OutputDisplayManager.displayText("Hai usato " + p.getItem().getName() + ".");
                        }
                    } else {
                        OutputDisplayManager.displayText("Non possiedi " + item.getName() + ".");
                    }
                }
        );

        // Lascia oggetto
        commandMap.put(new CommandExecutorKey(CommandType.LASCIA, 2),
                p -> {
                    Item item = p.getItem();
                    
                    if(!game.getInventory().contains(item)) {
                        OutputDisplayManager.displayText("Non possiedi " + item.getName() + ".");
                    } 
                    game.removeInventory(item);
                    game.getCurrentRoom().addItems(item);
                    OutputDisplayManager.displayText("Hai lasciato " + item.getName() + " nella stanza.");
                }
        );
    }

    public void execute(ParserOutput p) {

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