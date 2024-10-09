/**
 * La classe DBConnection gestiona la connexió amb la base de dades SQLite
 * utilitzada per l'aplicació del restaurant.
 */
package com.projecterestaurant.address.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public Connection connection;
    private String dataBase = "BBDDRestaurant.db";

    /**
     * Constructor de la classe DBConnection.
     */
    public DBConnection() {
    }

    /**
     * Estableix la connexió amb la base de dades SQLite.
     * Carrega el driver JDBC i crea una connexió amb la base de dades especificada.
     * Mostra un missatge si la connexió és correcta o si hi ha algun error.
     */
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataBase);
            System.out.println("Connexió correcta");
        } catch (SQLException ex) {
            System.out.println("No s'ha pogut connectar. " + ex.toString());
        } catch (ClassNotFoundException ex) {
            System.out.println("No s'ha pogut carregar el driver" + ex.toString());
        }
    }

    /**
     * Tanca la connexió amb la base de dades.
     * Mostra un missatge si no es pot tancar la connexió.
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("No s'ha pogut tancar la connexió. " + ex.toString());
        }
    }
}
