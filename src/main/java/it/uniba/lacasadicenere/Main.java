/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package it.uniba.lacasadicenere;

import it.uniba.lacasadicenere.database.DatabaseConnection;
import it.uniba.lacasadicenere.gui.ManagerGUI;
/**
 * Classe principale dell'applicazione.
 */
public class Main {

    /**
     * Metodo principale dell'applicazione.
     * @param args
     */
    public static void main(final String[] args) {
        new ManagerGUI();
        
        try {
            DatabaseConnection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
