/**
 * Classe que gestiona les operacions de persistència per a les reserves.
 */
package com.projecterestaurant.address.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.projecterestaurant.address.model.Clients;
import com.projecterestaurant.address.model.Reserves;
import com.projecterestaurant.address.model.Taula;

import javafx.scene.control.Alert;

public class ReservaDAO extends DBConnection implements DAO<Reserves, Integer> {
    // Declaració de les constants per a les consultes SQL
    private final String INSERT = "INSERT INTO RESERVES(id_client,id_taula,numero_comensals,data,hora_arribada,hora_sortida,pendent) VALUES(?,?,?,?,?,?,?)";
    private final String UPDATE = "UPDATE RESERVES SET id_client=?, id_taula=?, numero_comensals=?, data=?, hora_arribada=?, hora_sortida=? WHERE id_reserva=?";
    private final String UPDATEPENDENT = "UPDATE RESERVES SET pendent=? WHERE id_reserva=?";
    private final String DELETE = "DELETE FROM RESERVES WHERE id_reserva=?";
    private final String SELECTBYID = "SELECT * FROM RESERVES WHERE id_reserva=?";
    private final String SELECTALL = "SELECT * FROM RESERVES";
    private final String SELECTBYDATA = "SELECT * FROM reserves WHERE id_taula = ? AND data = ? AND ((hora_arribada >= ? AND hora_arribada < ?) OR (hora_sortida > ? AND hora_sortida <= ?) OR (hora_arribada <= ? AND hora_sortida >= ?));";

    @Override
    public void insert(Reserves t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERT);
            ps.setInt(1, t.getClient().getId_client());
            ps.setInt(2, t.getTaula().getId_taula());
            ps.setInt(3, t.getNumcomensals());
            ps.setString(4, t.getData().toString());
            ps.setString(5, t.getHora_arribada().toString());
            ps.setString(6, t.getHora_sortida().toString());
            ps.setBoolean(7, t.isPendent());
            if (ps.executeUpdate() != 0) {
                System.out.println("Reserva inserida correctament en BBDD.");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Reserva Inserir");
                alert.setContentText("Reserva Inserit Correctament");
                alert.showAndWait();
            }
            closeConnection();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error a l'hora d'insertar la reserva");
            alert.setHeaderText("Error a l'hora d' insertar la reserva.");
            alert.setContentText("No es pot reservar la mateixa taula al mateix dia i hora.");
            alert.showAndWait();
            System.out.println("ERROR BASE DE DADES");
        } catch (Exception e) {
            System.out.println("Error desconegut a l'hora d'insertar la reserva a la base de dades");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Reserves t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            //Agafem del objecte client que tenim adins de la reserva el id
            ps.setInt(1, t.getClient().getId_client());
            //Agafem del objecte taula que tenim adins de la reserva el id
            ps.setInt(2, t.getTaula().getId_taula());
            ps.setInt(3, t.getNumcomensals());
            ps.setString(4, t.getData().toString());
            ps.setString(5, t.getHora_arribada().toString());
            ps.setString(6, t.getHora_sortida().toString());
            ps.setInt(7, t.getId_reserva());
            if (ps.executeUpdate() != 0) {
                System.out.println("Reserva actualitzada correctament en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePendent(Reserves t){
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATEPENDENT);
            ps.setBoolean(1, t.isPendent());
            ps.setInt(2, t.getId_reserva());
            if (ps.executeUpdate() != 0) {
                System.out.println("Reserva actualitzada correctament en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Reserves t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETE);
            ps.setInt(1, t.getId_reserva());
            if (ps.executeUpdate() != 0) {
                System.out.println("Reserva eliminada correctament en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reserves selectById(Integer id) {
        Reserves reserva = new Reserves();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reserva.setId_reserva(rs.getInt("id_reserva"));
                ClientDAO clientDAO = new ClientDAO();
                //Obtenim el objecte del client de la reserva mitjançant el id del client
                reserva.setClient(clientDAO.selectById(rs.getInt("id_client")));
                TaulaDAO taulaDAO = new TaulaDAO();
                //Obtenim el objecte de la taula de la reserva mitjançant el id de la taula
                reserva.setTaula(taulaDAO.selectById(rs.getInt("id_taula")));
                reserva.setNumcomensals(rs.getInt("numero_comensals"));
                reserva.setData(rs.getDate("data").toLocalDate());
                reserva.setHora_arribada(rs.getTime("hora_arribada").toLocalTime());
                reserva.setHora_sortida(rs.getTime("hora_sortida").toLocalTime());
                reserva.setPendent(rs.getBoolean("pendent"));
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reserva;
    }

    @Override
    public List<Reserves> selectAll() {
        List<Reserves> reservesList = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reserves reserva = new Reserves();
                reserva.setId_reserva(rs.getInt("id_reserva"));
                ClientDAO clientDAO = new ClientDAO();
                //Obtenim el objecte del client de la reserva mitjançant el id del client
                reserva.setClient(clientDAO.selectById(rs.getInt("id_client")));
                TaulaDAO taulaDAO = new TaulaDAO();
                //Obtenim el objecte de la taula de la reserva mitjançant el id de la taula
                reserva.setTaula(taulaDAO.selectById(rs.getInt("id_taula")));
                reserva.setNumcomensals(rs.getInt("numero_comensals"));
                LocalDate data = LocalDate.parse(rs.getString("data"));
                reserva.setData(data);
                LocalTime timeArribada = LocalTime.parse(rs.getString("hora_arribada"));
                reserva.setHora_arribada(timeArribada);
                LocalTime timeSortida = LocalTime.parse(rs.getString("hora_sortida"));
                reserva.setHora_sortida(timeSortida);
                reserva.setPendent(rs.getBoolean("pendent"));
                reservesList.add(reserva);
            }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservesList;
    }


    public List<Reserves> selectByTaulaIdAndDataAndInterval(int taulaId, LocalDate data, LocalTime horaArribada,
            LocalTime horaSortida) {
        List<Reserves> reserves = new ArrayList<>();
        ClientDAO clientDAO = new ClientDAO();
        TaulaDAO taulaDAO = new TaulaDAO();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYDATA);
            ps.setInt(1, taulaId);
            ps.setString(2, data.toString());
            ps.setString(3, horaArribada.toString());
            ps.setString(4, horaSortida.toString());
            ps.setString(5, horaArribada.toString());
            ps.setString(6, horaSortida.toString());
            ps.setString(7, horaArribada.toString());
            ps.setString(8, horaSortida.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id_reserva = rs.getInt("id_reserva");
                int id_client = rs.getInt("id_client");
                //Obtenim el objecte del client de la reserva mitjançant el id del client
                Clients client = clientDAO.selectById(id_client); 
                int id_taula = rs.getInt("id_taula");
                 //Obtenim el objecte de la taula de la reserva mitjançant el id de la taula
                Taula taula = taulaDAO.selectById(id_taula); 
                int numcomensals = rs.getInt("numero_comensals");
                LocalDate fecha = LocalDate.parse(rs.getString("data"));
                LocalTime horaArribadaReserva = LocalTime.parse(rs.getString("hora_arribada"));
                LocalTime horaSortidaReserva = LocalTime.parse(rs.getString("hora_sortida"));
                boolean pendent = rs.getBoolean("pendent");
                Reserves reserva = new Reserves(id_reserva, client, taula, numcomensals, fecha, horaArribadaReserva,
                        horaSortidaReserva, pendent);
                reserves.add(reserva);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reserves;

    }
}
