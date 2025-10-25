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
                    .filter(c -> c.getStartingRoom().getName().equals(game.getCurrentRoom().getName()) && c.getDirection() == direction)
                    .findFirst()
                    .orElse(null);
            
            if (corridor != null && !corridor.isLocked()) {
                game.setCurrentRoom(corridor.getArrivingRoom());
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
        commandMap.put(new CommandExecutorKey(CommandType.OSSERVA, 0),
                p -> game.getCurrentRoom().printDescription());

        // Inventario
        commandMap.put(new CommandExecutorKey(CommandType.INVENTARIO, 0),
                p -> game.printInventory());
        
        // Prendi oggetto
        commandMap.put(new CommandExecutorKey(CommandType.PRENDI, 1),
                p -> {
                    Item item = p.getItem();

                    if (item == null) {
                        OutputDisplayManager.displayText("Oggetto non specificato.");
                        return;
                    }
                    
                    if (game.getInventory().contains(item)) {
                        OutputDisplayManager.displayText("Hai già " + item.getName() + " nell'inventario.");
                    } else if(game.getCurrentRoom().getItems().contains(p.getItem())) {
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
                
        // Osserva oggetto
        commandMap.put(new CommandExecutorKey(CommandType.OSSERVA, 1),
                p -> {
                    if(game.getCurrentRoom().getItems().contains(p.getItem())) {
                        DatabaseConnection.printFromDB("Osserva", game.getCurrentRoom().getName(), String.valueOf(game.getCurrentRoom()), p.getItem().getName());
                    } else if(game.getInventory().contains(p.getItem())) {
                        OutputDisplayManager.displayText("La tua borsa non è trasparente!");
                    } else {
                        OutputDisplayManager.displayText(p.getItem().getName() + " non è presente nella stanza.");
                    }
                });

        // Usa oggetto
        commandMap.put(new CommandExecutorKey(CommandType.USA, 1),
                p -> {
                    if(game.getInventory().contains(p.getItem())) {
                        boolean used = gameLogic.executeUseSingeItem(p.getItem());
                        if(!used) {
                            OutputDisplayManager.displayText("Non succede nulla usando " + p.getItem().getName() + ".");
                            return;
                        }
                    } else {
                        OutputDisplayManager.displayText("Non puoi usare qualcosa che non possiedi.");
                    }
                });

        // Lascia oggetto
        commandMap.put(new CommandExecutorKey(CommandType.LASCIA, 1),
                p -> {
                    Item item = p.getItem();
                    if (item == null) {
                        OutputDisplayManager.displayText("Oggetto non specificato.");
                        return;
                    }
                    
                    if (!game.getInventory().contains(item)) {
                        OutputDisplayManager.displayText("Non possiedi " + item.getName() + ".");
                        return;
                    }

                    if (game.getCurrentRoom() != null && game.getCurrentRoom().getName().equals("Stanza5")) {
                        boolean isEndingItem = item.hasName("Candela") || 
                                              item.hasName("Amuleto") || 
                                              item.hasName("Diario");
                        
                        if (isEndingItem) {
                            // Controlla se il giocatore ha tutti e tre gli oggetti
                            boolean hasCandela = game.getInventory().stream()
                                    .anyMatch(i -> i.hasName("Candela"));
                            boolean hasAmuleto = game.getInventory().stream()
                                    .anyMatch(i -> i.hasName("Amuleto"));
                            boolean hasDiario = game.getInventory().stream()
                                    .anyMatch(i -> i.hasName("Diario"));
                            
                            if (hasCandela && hasAmuleto && hasDiario) {
                                OutputDisplayManager.displayText("Posi " + item.getName() + " sull'altare...");
                                
                                // Attiva il finale
                                UserInputFlow.Event = 2;
                                return;
                            } else {
                                OutputDisplayManager.displayText("Posi " + item.getName() + " sull'altare, ma non succede nulla. Forse mancano altri oggetti...");
                                return;
                            }
                        }
                    }
                    game.removeInventory(item);
                    if (game.getCurrentRoom() != null) {
                        game.getCurrentRoom().addItems(item);
                    }
                    OutputDisplayManager.displayText("Hai lasciato " + item.getName() + " nella stanza.");
                });
    }

    /**
     * Esegue il comando appropriato in base all'output del parser.
     * 
     * @param p L'output del parser contenente comando e oggetto
     */
    // ...existing code...
    public void executeCommand(ParserOutput p) {
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