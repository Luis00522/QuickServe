/**
 * Classe que implementa un DAO per a la gestió de taules d'un restaurant.
 * Utilitza una connexió a una base de dades per realitzar les operacions CRUD.
 */
package com.projecterestaurant.address.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.projecterestaurant.address.enums.TipusTaula;
import com.projecterestaurant.address.model.Taula;

public class TaulaDAO extends DBConnection implements DAO<Taula, Integer> {

    // Sentències SQL per a les operacions CRUD
    private final String INSERT = "INSERT INTO TAULES(capacitat,tipus_taula,estat) VALUES(?,?,?)";
    private final String UPDATE = "UPDATE TAULES SET capacitat=?, tipus_taula=?,estat=? WHERE id_taula=?";
    private final String DELETE = "DELETE FROM TAULES WHERE id_taula=?";
    private final String SELECTBYID = "SELECT * FROM TAULES WHERE id_taula=?";
    private final String SELECTALL = "SELECT * FROM TAULES";

    /**
     * Insereix una nova taula a la base de dades.
     * 
     * @param t La taula a inserir.
     */
    @Override
    public void insert(Taula t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERT);
            ps.setInt(1, t.getCapacitat());
            ps.setString(2, t.getTipusTaula().name());
            ps.setBoolean(3, t.isEstat());
            if (ps.executeUpdate() != 0) {
                System.out.println("Taula inserida correctament en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualitza les dades d'una taula a la base de dades.
     * 
     * @param t La taula amb les dades actualitzades.
     */
    @Override
    public void update(Taula t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setInt(1, t.getCapacitat());
            ps.setString(2, t.getTipusTaula().name());
            ps.setBoolean(3, t.isEstat());
            ps.setInt(4, t.getId_taula());
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Taula actualizada correctament a la BBDD.");
            } else {
                System.out.println("No s'ha trobat la Taula a la BBDD.");
            }
            
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina una taula de la base de dades.
     * 
     * @param t La taula a eliminar.
     */
    @Override
    public void delete(Taula t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETE);
            ps.setInt(1, t.getId_taula());
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Taula eliminada correctament de la BBDD.");
            } else {
                System.out.println("No s'ha trobat la Taula a la BBDD.");
            }
            
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Seleciona una taula de la base de dades segons la seva ID.
     * 
     * @param id La ID de la taula a seleccionar.
     * @return La taula seleccionada, o null si no s'ha trobat.
     */
    @Override
    public Taula selectById(Integer id) {
        Taula taula = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYID);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idTaula = rs.getInt("id_taula");
                int capacitat = rs.getInt("capacitat");
                String tipustaulaCadena = rs.getString("tipus_taula");
                TipusTaula tipustaula = TipusTaula.valueOf(tipustaulaCadena);
                boolean estat = rs.getBoolean("estat");
                
                taula = new Taula(idTaula, capacitat, tipustaula, estat);
            }
            
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taula;
    }

    /**
     * Seleciona totes les taules de la base de dades.
     * 
     * @return Una llista de totes les taules de la base de dades.
     */
    @Override
    public List<Taula> selectAll() {
        ArrayList<Taula> taules = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idTaula = rs.getInt("id_taula");
                int capacitat = rs.getInt("capacitat");
                String tipustaulaCadena = rs.getString("tipus_taula");
                TipusTaula tipustaula = TipusTaula.valueOf(tipustaulaCadena);
                boolean estat = rs.getBoolean("estat");
                Taula taula= new Taula(idTaula,capacitat,tipustaula,estat);
                taules.add(taula);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taules;
    }
}
