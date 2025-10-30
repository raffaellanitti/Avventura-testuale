/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package it.uniba.lacasadicenere;

import it.uniba.lacasadicenere.database.DatabaseH2;
import it.uniba.lacasadicenere.ui.MainFrame;
/**
 * Classe principale dell'applicazione.
 */
public class Main {

    /**
     * Metodo principale dell'applicazione.
     * @param args
     */
    public static void main(final String[] args) {
        new MainFrame();
        
        try {
            DatabaseH2.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}