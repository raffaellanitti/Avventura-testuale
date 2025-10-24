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
 * Classe per la gestione della connessione al database.
 */
public class DatabaseConnection {
    
    /**
     * Driver JDBC per H2 Database.
     */
    static final String JDBC_DRIVER = "org.h2.Driver";

    /**
     * URL del database H2.
     */
    static final String DB_URL = "jdbc:h2:./src/main/resources/database";

    /**
     * Credenziali di accesso al database.
     */
    static final String USER = "sa";

    /**
     * Password di accesso al database.
     */
    static final String PASS = "";

    /**
     * Metodo per stabilire la connessione al database.
     * @return
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

            String sql2 = "SELECT * FROM DESCRIZIONE";
            stmt = conn.prepareStatement(sql2);
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
     * Metodo per chiudere la connessione al database.
     * @param conn
     */
    public static void close(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Metodo per stampare la descrizione dal database in base ai parametri forniti.
     * @param comando
     * @param stanza
     * @param stato
     * @param oggetto
     */
    public static void printFromDB(String comando, String stanza, String stato, String oggetto) {
        String query = "SELECT DESCRIZIONE FROM DESCRIZIONE WHERE COMANDO = ? AND STANZA = ? AND STATO = ? AND OGGETTO = ?";
        try (Connection conn = connect();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, comando.trim());
            stmt.setString(2, stanza.trim());
            stmt.setString(3, stato.trim());
            stmt.setString(4, oggetto.trim());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                OutputDisplayManager.displayText(rs.getString("DESCRIZIONE"));
            } else {
                OutputDisplayManager.displayText("Nessuna descrizione trovata per i parametri forniti.");
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e); 
        }
    }
}
