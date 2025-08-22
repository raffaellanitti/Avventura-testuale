/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.entity;

/**
 *
 * @author raffaellanitti
 */
public enum CommandType {
    
    // Comandi di movimento
    NORD,
    SUD,
    EST,
    OVEST, 
    SALI,
    SCENDI,
           
    // Comandi di interazione con l'ambiente e gli oggetti
    OSSERVA, // Per esaminare oggetti o la stanza
    PRENDI, // Per aggiungere un oggetto all'inventario
    USA, // Per utilizzare un oggetto, come la chiave o i fiammiferi
    ACCENDI, // Azione specifica per accendere la Candela Parlante
    PARLA, // Per interagire con la Candela Parlante
    SOLLEVA, // Per sollevare la mattonella crepata
    SPINGI, // Per spingere la mattonella (alternativa a solleva)
    APRI, // Per aprire un cassetto o il rubinetto
    LEGGI, // Per leggere un libro o un foglio
    INSERISCI, // Per inserire gli oggetti sull'altare
    
    // Comandi di gioco generici
    AIUTO,
    INVENTARIO,
    ESCI,
    
    // Comando per la risposta finale 
    RISPONDI;
}
