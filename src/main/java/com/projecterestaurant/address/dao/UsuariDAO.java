/**
 * Classe que implementa un DAO per a la gestió d'usuaris d'un restaurant.
 * Utilitza una connexió a una base de dades per realitzar les operacions CRUD.
 */
package com.projecterestaurant.address.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.projecterestaurant.address.model.Usuari;

public class UsuariDAO extends DBConnection implements DAO<Usuari, Integer> {

    // Sentències SQL per a les operacions CRUD
    private final String INSERT = "INSERT INTO USUARIS(nom,contrasenya) VALUES(?,?)";
    private final String UPDATE = "UPDATE USUARIS SET nom=?, contrasenya=? WHERE id_usuari=?";
    private final String DELETE = "DELETE FROM USUARIS WHERE id_usuari=?";
    private final String SELECTBYID = "SELECT * FROM USUARIS WHERE id_usuari=?";
    private final String SELECTALL = "SELECT * FROM USUARIS";
    private final String SELECTBYNOMANDPASSWORD = "SELECT * FROM USUARIS WHERE nom=? AND contrasenya=?";

    /**
     * Insereix un nou usuari a la base de dades.
     * 
     * @param t L'usuari a inserir.
     */
    @Override
    public void insert(Usuari t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERT);
            ps.setString(1, t.getNom());
            ps.setString(2, t.getPassword());
            if (ps.executeUpdate() != 0) {
                int id = ps.getGeneratedKeys().getInt(1);
                t.setId(id);
                System.out.println("Usuari inserit correctament en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualitza les dades d'un usuari a la base de dades.
     * 
     * @param t L'usuari amb les dades actualitzades.
     */
    @Override
    public void update(Usuari t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setString(1, t.getNom());
            ps.setString(2, t.getPassword());
            ps.setInt(3, t.getId());
            if (ps.executeUpdate() != 0) {
                System.out.println("Usuari modificat correctament en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un usuari de la base de dades.
     * 
     * @param t L'usuari a eliminar.
     */
    @Override
    public void delete(Usuari t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETE);
            ps.setInt(1, t.getId());
            if (ps.executeUpdate() != 0) {
                System.out.println("Usuari eliminat correctament en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Selecciona un usuari de la base de dades segons la seva ID.
     * 
     * @param id La ID de l'usuari a seleccionar.
     * @return L'usuari seleccionat, o null si no s'ha trobat.
     */
    @Override
    public Usuari selectById(Integer id) {
        Usuari usuari = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idUsuari = rs.getInt("id_usuari");
                String nom = rs.getString("nom");
                String password = rs.getString("contrasenya");
                usuari = new Usuari(nom, password);
                usuari.setId(idUsuari);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuari;
    }

    /**
     * Selecciona tots els usuaris de la base de dades.
     * 
     * @return Una llista de tots els usuaris de la base de dades.
     */
    @Override
    public List<Usuari> selectAll() {
        ArrayList<Usuari> usuaris = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idUsuari = rs.getInt("id_usuari");
                String nom = rs.getString("nom");
                String password = rs.getString("contrasenya");
                Usuari usuari = new Usuari(nom, password);
                usuari.setId(idUsuari);
                usuaris.add(usuari);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuaris;
    }

    /**
     * Selecciona un usuari de la base de dades segons el seu nom i contrasenya.
     * 
     * @param nom      El nom de l'usuari.
     * @param password La contrasenya de l'usuari.
     * @return L'usuari seleccionat, o null si no s'ha trobat.
     */
    public Usuari selectByNomAndPassword(String nom, String password) {
        Usuari usuari = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYNOMANDPASSWORD);
            ps.setString(1, nom);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idUsuari = rs.getInt("id_usuari");
                String nomUsuari = rs.getString("nom");
                String contrasenya = rs.getString("contrasenya");
                int supervisor = rs.getInt("supervisor");
                usuari = new Usuari(nomUsuari, contrasenya);
                usuari.setId(idUsuari);
                usuari.setSupervisor(supervisor);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuari;
    }
}
