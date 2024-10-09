package com.projecterestaurant.address.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.projecterestaurant.address.model.Clients;

/**
 * Classe per a l'accés a dades dels clients.
 */
public class ClientDAO extends DBConnection implements DAO<Clients, Integer> {
    private final String INSERT = "INSERT INTO CLIENTS(nom,cognom,telefon) VALUES(?,?,?)";
    private final String UPDATE = "UPDATE CLIENTS SET nom=?, cognom=?,telefon=? WHERE id_client=?";
    private final String DELETE = "DELETE FROM CLIENTS WHERE id_client=?";
    private final String SELECTBYID = "SELECT * FROM CLIENTS WHERE id_client=?";
    private final String SELECTALL = "SELECT * FROM CLIENTS";
    private final String COUNTCIENTS = "SELECT COUNT(*) FROM reserves WHERE id_client = ?";

    @Override
    public void insert(Clients c) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERT);
            ps.setString(1, c.getNom());
            ps.setString(2, c.getCognom());
            ps.setInt(3, c.getTelefon());
            if (ps.executeUpdate() != 0) {
                System.out.println("Client inserit correctament a la BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Clients c) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setString(1, c.getNom());
            ps.setString(2, c.getCognom());
            ps.setInt(3, c.getTelefon());
            ps.setInt(4, c.getId_client());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Client actualizat correctament a la BBDD.");
            } else {
                System.out.println("No sa trobat el client a la BBDD.");
            }

            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Clients c) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETE);
            ps.setInt(1, c.getId_client());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Client eliminat correctament de la BBDD.");
            } else {
                System.out.println("No s'ha trobat el Client a la BBDD.");
            }

            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Clients selectById(Integer id) {
        Clients client = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYID);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idClient = rs.getInt("id_client");
                String nom = rs.getString("nom");
                String cognom = rs.getString("cognom");
                int telefon = rs.getInt("telefon");

                client = new Clients(idClient, nom, cognom, telefon);

            }

            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public List<Clients> selectAll() {
        ArrayList<Clients> clients = new ArrayList<>();
        Clients client = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idClient = rs.getInt("id_client");
                String nom = rs.getString("nom");
                String cognom = rs.getString("cognom");
                int telefon = rs.getInt("telefon");

                client = new Clients(idClient, nom, cognom, telefon);
                clients.add(client);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clients;
    }

    /**
     * Comprova si un client té reserves.
     * 
     * @param clientId ID del client
     * @return True si el client té reserves, False altrament
     */
    public boolean clientHasReservation(int clientId) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(COUNTCIENTS);
            ps.setInt(1, clientId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }
}
