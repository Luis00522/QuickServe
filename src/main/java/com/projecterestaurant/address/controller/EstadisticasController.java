/**
 * La classe EstadisticasController és responsable de gestionar les estadístiques de reserves 
 * en l'aplicació del restaurant. Aquesta classe carrega les dades de les reserves, les filtra 
 * per dies laborables i mostra aquestes dades en un gràfic de barres.
 */
package com.projecterestaurant.address.controller;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.projecterestaurant.App;
import com.projecterestaurant.address.dao.ReservaDAO;
import com.projecterestaurant.address.model.Reserves;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class EstadisticasController  {
    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private Button btnTaules;

    private ReservaDAO reservaDAO = new ReservaDAO();

    private List<Reserves> reservas;

    @FXML
    private Button btnExit;

    /**
     * Inicialitza el controlador. Carrega les dades de les reserves, les filtra per dies laborables
     * i mostra les dades en un gràfic de barres.
     */
    @FXML
    public void initialize() {
        loadReservasData();
        filterReservasByWeekdays();
        displayDataOnChart();
    }

    /**
     * Carrega totes les reserves des de la base de dades.
     */
    private void loadReservasData() {
        reservas = reservaDAO.selectAll();
    }

    /**
     * Filtra les reserves per mostrar només aquelles que estan dins de la setmana actual.
     */
    private void filterReservasByWeekdays() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        reservas = reservas.stream()
                .filter(reserva -> reserva.getData().isAfter(startOfWeek.minusDays(1)) && reserva.getData().isBefore(endOfWeek.plusDays(1)))
                .collect(Collectors.toList());
    }

    /**
     * Mostra les dades filtrades en un gràfic de barres, agrupades per dies de la setmana.
     */
    private void displayDataOnChart() {
        barChart.getData().clear();

        // Creem una sèrie per cada dia de la setmana
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Reserves");

        // Contem les reserves per cada dia de la setmana
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            long count = reservas.stream()
                    .filter(reserva -> reserva.getData().getDayOfWeek() == dayOfWeek)
                    .count();
            series.getData().add(new XYChart.Data<>(dayOfWeek.toString(), (int) count));
        }

        // Afegim les dades al gràfic
        barChart.getData().add(series);
    }

    /**
     * Navega a la vista de gestió de taules.
     */
    @FXML
    private void gestioDeTaules() {
        try {
            btnTaules.getScene().getWindow().hide();
            Scene scenaGestioTaules = new Scene(App.loadFXML("gestiotaula"));
            Stage stage = new Stage();
            stage.setTitle("Gestió de Taula");
            stage.setResizable(true);
            stage.setScene(scenaGestioTaules);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No es pot carregar la vista de la gestió de taules.");
            alert.showAndWait();
        }
    }

    /**
     * Tanca la sessió i torna a la vista de login.
     */
    @FXML
    private void exit() {
        try {
            btnExit.getScene().getWindow().hide();
            Scene scenaLogin = new Scene(App.loadFXML("loginview"));
            Stage stage = new Stage();
            stage.setTitle("LOGIN APP");
            stage.setResizable(false);
            stage.setScene(scenaLogin);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No es pot carregar la vista del login.");
            alert.showAndWait();
        }
    }
}
