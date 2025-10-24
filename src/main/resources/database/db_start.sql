CREATE TABLE IF NOT EXISTS DESCRIZIONE (
    ID INT PRIMARY KEY,
    COMANDO VARCHAR(255),       -- Comando del giocatore: Osserva, Prendi, Usa, Parla, Fine
    STANZA VARCHAR(255),        -- Nome della stanza in cui si trova il comando/oggetto
    STATO VARCHAR(255),         -- Stato della stanza/oggetto se necessario 
    OGGETTO VARCHAR(255),       -- Nome dell’oggetto coinvolto (se presente)
    DESCRIZIONE CLOB(10000)     -- Testo della descrizione o dialogo
);