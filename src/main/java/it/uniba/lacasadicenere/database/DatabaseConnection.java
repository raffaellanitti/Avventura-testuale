/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.uniba.lacasadicenere.database;

import it.uniba.lacasadicenere.interactionManager.OutputDisplayManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe per la gestione della connessione al database H2.
 * Gestisce la connessione, l'inizializzazione e le query al database.
 */
public class DatabaseConnection {
    
    /**
     * Driver JDBC per H2 Database.
     */
    private static final String JDBC_DRIVER = "org.h2.Driver";

    /**
     * URL del database H2.
     * Nota: Il database viene creato nella directory corrente del progetto.
     */
    private static final String DB_URL = "jdbc:h2:./src/main/resources/database";

    /**
     * Credenziali di accesso al database.
     */
    private static final String USER = "sa";
    private static final String PASS = "";
    
    /**
     * Stabilisce una connessione al database H2 e inizializza le tabelle se necessario.
     * 
     * @return La connessione al database
     * @throws RuntimeException Se si verifica un errore durante la connessione
     */
    public static Connection connect() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String start = "RUNSCRIPT FROM 'src/main/resources/database/db_start.sql'";
        String fill = "RUNSCRIPT FROM 'src/main/resources/database/db_info.sql'";

        boolean emptyDescr = true;
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement(start);
            stmt.execute();
            stmt.close();

            String checkQuery = "SELECT * FROM DESCRIZIONE";
            stmt = conn.prepareStatement(checkQuery);
            rs = stmt.executeQuery();
            while(rs.next()) {
                emptyDescr = false;
            }
            rs.close();
            
            if(emptyDescr) {
                stmt = conn.prepareStatement(fill);
                stmt.execute();
                stmt.close();
            }
            
            return conn;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } 
    }

    /**
     * Chiude la connessione al database.
     * 
     * @param conn La connessione da chiudere
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 
     * @param comando
     * @param stanza
     * @param stato
     * @param oggetto1
     * @param oggetto2 
     */
    public static void printFromDB(String comando, String stanza, String stato, String oggetto1, String oggetto2) {
        String query = "SELECT DESCRIZIONE FROM DESCRIZIONE WHERE COMANDO = ? AND STANZA = ? AND STATO = ? AND OGGETTO1 = ? AND OGGETTO2 = ?";
    
        System.out.println("=== DEBUG DATABASE ===");
        System.out.println("Query parametri:");
        System.out.println("  comando='" + comando + "'");
        System.out.println("  stanza='" + stanza + "'");
        System.out.println("  stato='" + stato + "'");
        System.out.println("  oggetto1='" + oggetto1 + "'");
        System.out.println("  oggetto2='" + oggetto2 + "'");
    
        try (Connection conn = connect();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, comando.trim());
            stmt.setString(2, stanza.trim());
            stmt.setString(3, stato.trim());
            stmt.setString(4, oggetto1.trim());
            stmt.setString(5, oggetto2.trim());

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
            String desc = rs.getString("DESCRIZIONE");
            System.out.println("  TROVATO: " + desc.substring(0, Math.min(50, desc.length())) + "...");
            System.out.println("======================");
            OutputDisplayManager.displayText(desc);
        } else {
            System.out.println("  NESSUN RISULTATO!");
            System.out.println("======================");
            
            String debugQuery = "SELECT ID, COMANDO, STANZA, OGGETTO1, OGGETTO2 FROM DESCRIZIONE";
            PreparedStatement debugStmt = conn.prepareStatement(debugQuery);
            ResultSet debugRs = debugStmt.executeQuery();
            
            System.out.println("--- CONTENUTO TABELLA DESCRIZIONE ---");
            int count = 0;
            while(debugRs.next() && count < 10) {
                System.out.println("ID=" + debugRs.getInt("ID") + 
                    ", COMANDO='" + debugRs.getString("COMANDO") + 
                    "', STANZA='" + debugRs.getString("STANZA") + 
                    "', OGG1='" + debugRs.getString("OGGETTO1") + 
                    "', OGG2='" + debugRs.getString("OGGETTO2") + "'");
                count++;
            }
            System.out.println("--------------------------------------");
            OutputDisplayManager.displayText("Nessuna descrizione per i parametri forniti.");
        }
        rs.close();
    } catch (SQLException e) {
        System.err.println("ERRORE SQL: " + e.getMessage());
        throw new RuntimeException(e);
    }
}
}
 