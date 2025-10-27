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

import org.h2.store.Data;

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
        System.out.println("=== DEBUG MOVIMENTO ===");
        System.out.println("Direzione: " + direction);
        System.out.println("Stanza corrente: " + game.getCurrentRoom().getName());
        
        Corridor corridor = game.getCorridorMap().stream()
                .filter(c -> c.getStartingRoom().getName().equals(game.getCurrentRoom().getName()) 
                          && c.getDirection() == direction)
                .findFirst()
                .orElse(null);
        
        System.out.println("Corridoio trovato: " + (corridor != null ? "SI" : "NO"));
        
        if(corridor != null) {
            System.out.println("Corridoio bloccato: " + corridor.isLocked());
            System.out.println("Stanza di arrivo: " + corridor.getArrivingRoom().getName());
        }
        
        if(corridor != null && !corridor.isLocked()) {
            game.setCurrentRoom(corridor.getArrivingRoom());
            System.out.println("Movimento riuscito! Nuova stanza: " + game.getCurrentRoom().getName());
            // Mostra automaticamente la descrizione della nuova stanza
            DatabaseConnection.printFromDB("Osserva", game.getCurrentRoom().getName(), "true", "0", "0");
        } else if (corridor != null && corridor.isLocked()) {
            System.out.println("Corridoio bloccato!");
            OutputDisplayManager.displayText("Il corridoio verso " + direction + " è bloccato!");
        } else {
            System.out.println("Corridoio non esiste!");
            OutputDisplayManager.displayText("Non c'è un passaggio verso " + direction + "!");
        }
        System.out.println("=======================");
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
                p -> DatabaseConnection.printFromDB("Osserva", game.getCurrentRoom().getName(), "true", "0", "0"));

        commandMap.put(new CommandExecutorKey(CommandType.INVENTARIO, 0),
                p -> game.printInventory());
        
        commandMap.put(new CommandExecutorKey(CommandType.OSSERVA, 1),
                p -> {
                    if(game.getCurrentRoom().getItems().contains(p.getItem1())) {
                        DatabaseConnection.printFromDB("Osserva", game.getCurrentRoom().getName(), "true", p.getItem1().getName(), "0");
                    } else if(game.getInventory().contains(p.getItem1())) {
                        OutputDisplayManager.displayText("Hai " + p.getItem1().getName() + " nell'inventario.");
                    } else {
                        OutputDisplayManager.displayText("Non c'è " + p.getItem1().getName() + " qui.");
                    }
                });
        
        commandMap.put(new CommandExecutorKey(CommandType.PRENDI, 1),
                p -> {
                    if (p.getItem1() == null) {
                        OutputDisplayManager.displayText("L'oggetto specificato non è stato riconosciuto o non è disponibile in questo momento.");
                        return;
                    }
                    
                    if(game.getInventory().contains(p.getItem1())) {
                        OutputDisplayManager.displayText("Hai già " + p.getItem1().getName() + " nell'inventario.");
                        return;
                    } 
                    if(game.getCurrentRoom().getItems().contains(p.getItem1())) {
                        if(p.getItem1().isPickable()) {
                            game.addInventory(p.getItem1());
                            game.getCurrentRoom().removeItem(p.getItem1().getName());
                            OutputDisplayManager.displayText("Hai raccolto " + p.getItem1().getName() + ".");
                        } else {
                            OutputDisplayManager.displayText(p.getItem1().getName() + " non può essere raccolto.");
                        }
                        return;
                    }
                    
                    ItemContainer parentContainer = findItemInContainers(p.getItem1());

                    if (parentContainer != null) {
                        if (p.getItem1().isPickable()) { 
                            parentContainer.getContainedItems().remove(p.getItem1());
                            game.addInventory(p.getItem1());
                            gameLogic.executePostPickupEffects(p.getItem1(), parentContainer); 
                            OutputDisplayManager.displayText("Hai preso " + p.getItem1().getName() + " da " + parentContainer.getName() + ".");
                        } else {
                            OutputDisplayManager.displayText("Non puoi raccogliere " + p.getItem1().getName() + " da " + parentContainer.getName() + ".");
                        }
                        return;
                    }
                    OutputDisplayManager.displayText(p.getItem1().getName() + " non è nella stanza.");
                });

        commandMap.put(new CommandExecutorKey(CommandType.USA, 1),
                p -> {
                    if(game.getInventory().contains(p.getItem1())) {
                        boolean used = gameLogic.executeUseSingleItem(p.getItem1());
                        if(!used) {
                            OutputDisplayManager.displayText("Non succede nulla usando " + p.getItem1().getName() + ".");
                            return;
                        }
                        DatabaseConnection.printFromDB("Usa", game.getCurrentRoom().getName(), "true", p.getItem1().getName(), "0");
                    } else {
                        OutputDisplayManager.displayText("Non possiedi questo oggetto.");
                    }
                });

        commandMap.put(new CommandExecutorKey(CommandType.USA, 2),
                p -> {
                    if(!game.getInventory().contains(p.getItem1())) {
                        OutputDisplayManager.displayText("Non possiedi " + p.getItem1().getName() + ".");
                        return; 
                    }
                    if(p.getItem2() == null) {
                        OutputDisplayManager.displayText("Devi specificare il secondo oggetto da usare.");
                        return;
                    }
                    boolean hasItem2InInventory = game.getInventory().contains(p.getItem2());
            boolean hasItem2InRoom = game.getCurrentRoom().getItems().contains(p.getItem2());
            
            if(!hasItem2InInventory && !hasItem2InRoom) {
                OutputDisplayManager.displayText("Non puoi usare " + p.getItem1().getName() + 
                    " con " + p.getItem2().getName() + " perché non è disponibile.");
                return;
            }
            boolean used = gameLogic.executeUseDoubleItem(p.getItem1(), p.getItem2());
            if(!used) {
                OutputDisplayManager.displayText("Non succede nulla usando " + 
                    p.getItem1().getName() + " con " + p.getItem2().getName() + ".");
            }

        });
        
        commandMap.put(new CommandExecutorKey(CommandType.LASCIA, 1), 
                p -> {
                    if (!game.getInventory().contains(p.getItem1())) {
                         OutputDisplayManager.displayText("Non possiedi " + p.getItem1().getName() + ".");
                        return;
                    }

                    game.removeInventory(p.getItem1());
                    if (game.getCurrentRoom() != null) {
                         game.getCurrentRoom().addItems(p.getItem1());
                    }
                    OutputDisplayManager.displayText("Hai lasciato " + p.getItem1().getName() + " nella stanza.");
             });

    }
    
    // Aggiungere un metodo helper alla classe CommandExecutor:
    /**
    * Cerca un oggetto all'interno dei contenitori nella stanza corrente o nell'inventario.
    */
    private ItemContainer findItemInContainers(Item item) {
        // Cerca nei contenitori nella stanza
        if (game.getCurrentRoom().getItems() != null) {
            for (Item roomItem : game.getCurrentRoom().getItems()) {
                if (roomItem instanceof ItemContainer) {
                    ItemContainer container = (ItemContainer) roomItem;
                    if (container.getContainedItems() != null && container.getContainedItems().contains(item)) {
                        return container;
                    }
                }
            }
        }
        // Cerca nei contenitori nell'inventario
        if (game.getInventory() != null) {
            for (Item inventoryItem : game.getInventory()) {
                if (inventoryItem instanceof ItemContainer) {
                    ItemContainer container = (ItemContainer) inventoryItem;
                    if (container.getContainedItems() != null && container.getContainedItems().contains(item)) {
                        return container;
                    }
                }
            }
        }
        return null;
    }
    /**
     * Esegue il comando appropriato in base all'output del parser.
     * 
     * @param p L'output del parser contenente comando e oggetto
     */
    public void execute(ParserOutput p) {
        System.out.println("=== DEBUG COMMAND EXECUTOR ===");
        System.out.println("Comando ricevuto: " + p.getCommand());
        System.out.println("Item1: " + (p.getItem1() != null ? p.getItem1().getName() : "null"));
        System.out.println("Item2: " + (p.getItem2() != null ? p.getItem2().getName() : "null"));
        System.out.println("Args: " + p.getArgs());
        System.out.println("==============================");
    
        //boolean hasItem1 = (p.getItem1() != null);
        //boolean hasItem2 = (p.getItem2() != null);
        //int args = hasItem1 ? (hasItem2 ? 2 : 1) : 0;

        int args = p.getArgs();
        CommandExecutorKey key = new CommandExecutorKey(p.getCommand(), args);
        
        System.out.println("Chiave cercata: comando=" + p.getCommand() + ", args=" + args);
    
        CommandBehavior behavior = commandMap.get(key);

        if (behavior != null) {
            System.out.println("Comportamento trovato! Esecuzione...");
            behavior.execute(p);
        } else {
            System.out.println("Comportamento NON trovato!");
            OutputDisplayManager.displayText("Comando non riconosciuto o non valido con gli argomenti forniti.");
        }
    }
}