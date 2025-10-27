CREATE TABLE IF NOT EXISTS DESCRIZIONE (
    ID INT PRIMARY KEY,
    COMANDO VARCHAR(255),       -- Comando del giocatore: Osserva, Prendi, Usa, Parla, Fine
    STANZA VARCHAR(255),        -- Nome della stanza in cui si trova il comando/oggetto
    STATO VARCHAR(255),         -- Stato della stanza/oggetto se necessario 
    OGGETTO1 VARCHAR(255),       -- Nome dell’oggetto coinvolto 
    OGGETTO2 VARCHAR(255),
    DESCRIZIONE CLOB(10000)     -- Testo della descrizione o dialogo
);