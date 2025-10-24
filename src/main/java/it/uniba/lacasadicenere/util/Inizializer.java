/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.uniba.lacasadicenere.entity.Game;
import it.uniba.lacasadicenere.entity.Item;
import it.uniba.lacasadicenere.entity.ItemContainer;
import it.uniba.lacasadicenere.entity.Room;
import it.uniba.lacasadicenere.entity.Corridor;
import it.uniba.lacasadicenere.type.CommandType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Classe per l'inizializzazione del gioco.
 */
public class Inizializer {
    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        Game game = Game.getInstance();
        
        // Creazione degli oggetti di gioco
        Item telefono = new Item("Telefono", "", true,
                Arrays.asList("Telefonino", "Cellulare", "Phone"));
        items.add(telefono);
        
        Item fiammiferi = new Item("Fiammiferi", "", true, 
                Arrays.asList("Fiammifer", "ScatolaFiammiferi", "Accendino"));
        items.add(fiammiferi);
        
        Item tavolo = new Item("Tavolo", "Un tavolino su cui giacciono una candela e un foglio piegato, ricoperto da un velo di cenere sottile.", 
                false, Arrays.asList("Tavolino", "Banco", "Tavola", "Scrivania"));
        items.add(tavolo);
        
        Item candela = new Item("Candela", "Una candela spenta, consumata dal tempo, ma ancora utilizzabile.", true, 
                Arrays.asList("Lume", "Luce", "Fiamma"));
        items.add(candela);
        
        Item foglio = new Item("Foglio", "Un foglio ricoperto di cenere. Leggi: 'Verso nord, la luce trova ciò che l’ombra nasconde.'", true, 
                Arrays.asList("Foglietto", "Indizio", "Carta", "Pagina"));
        items.add(foglio);
        
        Item camino = new Item("Camino", "Sopra il camino, tra la polvere e la fuliggine, noti una vecchia chiave di ferro che riflette la luce della candela.", false, 
                Arrays.asList("Caminetto", "Fuoco", "Focolare"));
        items.add(camino);
        
        Item tappeto = new Item("Tappeto", "Un vecchio tappeto logoro, pieno di macchie di cera e bruciature. Uno scrigno antico si trova proprio sul tappeto.", false, 
                Arrays.asList("Tappetino", "Stuoia"));
        items.add(tappeto);
        
        Item chiave = new Item("Chiave", "Una vecchia chiave di ferro.", true, 
                Arrays.asList("Chiavetta", "ChiaveFerro"));
        items.add(chiave);
        
        Item amuleto = new Item("Amuleto", "Un amuleto d'argento a forma di goccia. E' freddo al tatto e senti una vibrazione leggera: come se ti riconoscesse.", true, 
                Arrays.asList("Ciondolo", "Pendente", "Collana"));
        items.add(amuleto);

        Item scrigno = new ItemContainer("Scrigno", "Uno scrigno antico, con una serratura arrugginita. Sembra poter essere aperto con la chiave trovata sul camino. All’interno troverai un amuleto d’argento a forma di goccia.", false,
                Arrays.asList("Cassetta", "Cofanetto", "Baule", "Cassa"),
                Arrays.asList(amuleto)
        );
        items.add(scrigno);
          
        Item scaffale = new Item("Scaffale", "Tra i libri carbonizzati, un diario attira la tua attenzione: la copertina è intatta, di cuoio nero, con un simbolo inciso.", false, 
                Arrays.asList("Ripiano", "Mensola", "Palchetto"));
        items.add(scaffale);
        
        Item diario = new Item("Diario", "Sfogli alcune pagine: sono piene di appunti e simboli. Parla di un rituale per riportare la casa alla pace, e cita tre oggetti sacri: luce, protezione e memoria.", true, 
                Arrays.asList("Libro", "Quaderno", "Manoscritto"));
        items.add(diario);
        
        Item altare = new Item("Altare", "Un altare su cui vi è un’iscrizione incisa che recita: “Riporta la luce, la memoria e la protezione. Solo allora la casa avrà pace.", false, 
                Arrays.asList("Tavolo", "Piattaforma"));
        items.add(altare);
        
        // Creazione delle stanze
        Room stanza1 = new Room("Stanza1", "Sei nell’ingresso della casa. L’aria è fredda e immobile, e ogni passo fa scricchiolare il pavimento sotto i tuoi piedi. Un tavolino impolverato cattura la tua attenzione.", null);
        Room stanza2 = new Room("Stanza2", "Entri in un salone ampio e silenzioso. La luce della candela tremola sulle pareti annerite dal fumo. Un grande camino domina la stanza: la cenere al suo interno è fredda, ma qualcosa brilla tra le ombre. Sul pavimento, un tappeto consunto emana un odore di muffa e copre parte delle assi scricchiolanti.", null);
        Room stanza3 = new Room("Stanza3", "La porta si apre su una piccola biblioteca. L’odore di carta bruciata e muffa ti avvolge. Gli scaffali sono piegati dal peso dei volumi anneriti. Solo uno sembra intatto, come se qualcuno lo avesse protetto dal tempo.", null);
        Room stanza4 = new Room("Stanza4", "Appena entri, vieni avvolto da un silenzio irreale. Le pareti sono completamente ricoperte di specchi antichi, incrinati in più punti, che riflettono la luce tremolante della candela moltiplicando la tua immagine all’infinito. All’improvviso, senti il telefono vibrare nel taschino.", null);
        Room stanza5 = new Room("Stanza5", "Una fredda penombra avvolge la cripta. Davanti a te si erge un altare di pietra decorato con simboli identici a quelli trovati nelle stanze precedenti.", null);
        
        stanza1.addItems(items.stream()
                .filter(i -> i.getName().equals("Tavolo") || 
                        i.getName().equals("Foglio") || 
                        i.getName().equals("Candela"))
                .toArray(Item[]::new));
        stanza2.addItems(items.stream()
                .filter(i -> i.getName().equals("Camino") || 
                        i.getName().equals("Tappeto") || 
                        i.getName().equals("Chiave") || 
                        i.getName().equals("Scrigno"))
                .toArray(Item[]::new));
        stanza3.addItems(items.stream()
                .filter(i -> i.getName().equals("Scaffale") || 
                        i.getName().equals("Diario"))
                .toArray(Item[]::new));
        stanza5.addItems(items.stream()
                .filter(i -> i.getName().equals("Altare"))
                .toArray(Item[]::new));
        
        // Creazione dei corridoi
        Corridor c1a = new Corridor();
        c1a.setStartingRoom(stanza1);
        c1a.setDirection(CommandType.NORD);
        c1a.setLocked(true);
        c1a.setArrivingRoom(stanza2);
        
        Corridor c1b = new Corridor();
        c1b.setStartingRoom(stanza2);
        c1b.setDirection(CommandType.SUD);
        c1b.setLocked(false);
        c1b.setArrivingRoom(stanza1);
        
        Corridor c2a = new Corridor();
        c2a.setStartingRoom(stanza2);
        c2a.setDirection(CommandType.EST);
        c2a.setLocked(true);
        c2a.setArrivingRoom(stanza3);
        
        Corridor c2b = new Corridor();
        c2b.setStartingRoom(stanza3);
        c2b.setDirection(CommandType.OVEST);
        c2b.setLocked(false);
        c2b.setArrivingRoom(stanza2);
        
        Corridor c3a = new Corridor();
        c3a.setStartingRoom(stanza3);
        c3a.setDirection(CommandType.NORD);
        c3a.setLocked(true);
        c3a.setArrivingRoom(stanza4);
        
        Corridor c3b = new Corridor();
        c3b.setStartingRoom(stanza4);
        c3b.setDirection(CommandType.SUD);
        c3b.setLocked(false);
        c3b.setArrivingRoom(stanza3);
        
        Corridor c4a = new Corridor();
        c4a.setStartingRoom(stanza4);
        c4a.setDirection(CommandType.NORD);
        c4a.setLocked(true);
        c4a.setArrivingRoom(stanza5);
        
        Corridor c4b = new Corridor();
        c4b.setStartingRoom(stanza5);
        c4b.setDirection(CommandType.SUD);
        c4b.setLocked(false);
        c4b.setArrivingRoom(stanza4);
        
        List<Corridor> corridoi = Arrays.asList( 
                c1a, c1b, c2a, c2b, c3a, c3b, c4a, c4b
        );
        
        game.setCorridorMap(corridoi);
        
        game.setCurrentRoom(stanza1);
        game.setCurrentTime("00:00:00");
        
        // Creazione dell'inventario iniziale
        List<Item> inventario = new ArrayList<>();
        inventario.add(telefono);
        inventario.add(fiammiferi);
        game.getInventory().addAll(inventario);
        
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Item.class, new ItemDeserializer())
                .create();

        String gameJson = gson.toJson(game);
        writeJsonToFile("src/main/resources/json/Game.json", gameJson);

        String itemsJson = gson.toJson(items);
        writeJsonToFile("src/main/resources/json/Items.json", itemsJson);
    }

    
    /**
    * Metodo per scrivere il contenuto JSON in un file.
    * @param filePath
    * @param jsonContent
    */
    private static void writeJsonToFile(String filePath, String jsonContent) {
        Path path = Paths.get(filePath);
        try {
            Files.createDirectories(path.getParent()); 

            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(jsonContent);
                System.out.println("File creato con successo: " + filePath);
            }
        } catch (IOException e) {
        System.err.println("Errore di I/O durante la scrittura del file: " + filePath);
        e.printStackTrace();
        }
    }
}

