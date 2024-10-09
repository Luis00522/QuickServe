/**
 * La classe GestioReservesController és responsable de gestionar les reserves en l'aplicació del restaurant.
 * Aquesta classe permet veure, inserir, actualitzar, finalitzar i anul·lar reserves, així com seleccionar clients i taules.
 */
package com.projecterestaurant.address.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import com.projecterestaurant.App;
import com.projecterestaurant.address.dao.ClientDAO;
import com.projecterestaurant.address.dao.ReservaDAO;
import com.projecterestaurant.address.dao.TaulaDAO;
import com.projecterestaurant.address.model.Clients;
import com.projecterestaurant.address.model.Reserves;
import com.projecterestaurant.address.model.Taula;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GestioReservesController {
    @FXML
    private TableView<Reserves> tableViewReserves;
    @FXML
    private TableColumn<Reserves, Integer> columnIdReserva;
    @FXML
    private TableColumn<Reserves, Integer> columnIdClient;
    @FXML
    private TableColumn<Reserves, Integer> columnNumComen;
    @FXML
    private TableColumn<Reserves, Integer> columnIdTaula;
    @FXML
    private TableColumn<Reserves, LocalDate> columnData;
    @FXML
    private TableColumn<Reserves, LocalTime> columnHarribada;
    @FXML
    private TableColumn<Reserves, LocalTime> columnHsortida;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnClients;

    @FXML
    private TextField idReservaText;
    @FXML
    private TextField idTaulaText;
    @FXML
    private TextField idClientText;
    @FXML
    private TextField numComen;
    @FXML
    private DatePicker dataText;
    @FXML
    private TextField horaArribadaText;
    @FXML
    private TextField horaSortidaText;

    private ObservableList<Reserves> reservesList;

    private Reserves r;

    ReservaDAO reservaDAO = new ReservaDAO();

    /**
     * Inicialitza el controlador. Configura les columnes de la taula i carrega les dades inicials.
     */
    @FXML
    public void initialize() {
        // Carreguem les dades en les columnes de la TableView
        columnIdReserva.setCellValueFactory(new PropertyValueFactory<>("id_reserva"));
        columnIdClient.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        columnIdTaula.setCellValueFactory(new PropertyValueFactory<>("id_taula"));
        columnNumComen.setCellValueFactory(new PropertyValueFactory<>("numcomensals"));
        columnData.setCellValueFactory(new PropertyValueFactory<>("data"));
        columnHarribada.setCellValueFactory(new PropertyValueFactory<>("hora_arribada"));
        columnHsortida.setCellValueFactory(new PropertyValueFactory<>("hora_sortida"));

        loadReservesData();
    }

    /**
     * Carrega les dades de les reserves des de la base de dades i les mostra en la taula.
     */
    private void loadReservesData() {
        // Mostrem en la TableView només les reserves que estan pendents per fer
        List<Reserves> allReserves = reservaDAO.selectAll();
        List<Reserves> filteredReserves = allReserves.stream()
                .filter(reserva -> !reserva.isPendent())
                .collect(Collectors.toList());

        reservesList = FXCollections.observableArrayList(filteredReserves);
        this.tableViewReserves.setItems(reservesList);
    }

    /**
     * Mostra els detalls de la reserva seleccionada en els camps de text.
     *
     * @param event l'esdeveniment de clic del ratolí
     */
    @FXML
    private void veureDetallsTaula(MouseEvent event) {
        // Mètode per mostrar les dades dels objectes en la TableView
        r = tableViewReserves.getSelectionModel().getSelectedItem();
        if (r != null) {
            idReservaText.setText(String.valueOf(r.getId_reserva()));
            idClientText.setText(String.valueOf(r.getClient().getId_client()));
            idTaulaText.setText(String.valueOf(r.getTaula().getId_taula()));
            numComen.setText(String.valueOf(r.getNumcomensals()));
            dataText.setValue(r.getData());
            horaArribadaText.setText(String.valueOf(r.getHora_arribada()));
            horaSortidaText.setText(String.valueOf(r.getHora_sortida()));
        }
    }

    /**
     * Actualitza les dades de la reserva seleccionada.
     */
    @FXML
    private void actualizarReserva() {
        ClientDAO clientDAO = new ClientDAO();
        TaulaDAO taulaDAO = new TaulaDAO();
        int idReserva = Integer.parseInt(idReservaText.getText());
        Clients client = clientDAO.selectById(Integer.parseInt(idClientText.getText()));
        Taula taula = taulaDAO.selectById(Integer.parseInt(idTaulaText.getText()));
        int numcomensals = Integer.parseInt(numComen.getText());
        LocalDate data = dataText.getValue();
        LocalTime horaArribada = LocalTime.parse(horaArribadaText.getText());
        LocalTime horaSortida = LocalTime.parse(horaSortidaText.getText());

        Reserves reservaActualitzar = new Reserves(idReserva, client, taula, numcomensals, data, horaArribada,
                horaSortida);

        reservaDAO.update(reservaActualitzar);
        loadReservesData();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Actualitzar Reserva");
        alert.setContentText("Reserva actualitzada correctament");
        alert.showAndWait();
    }

    /**
     * Insereix una nova reserva amb les dades proporcionades.
     */
    @FXML
    private void inserirReserva() {
        ClientDAO clientDAO = new ClientDAO();
        TaulaDAO taulaDAO = new TaulaDAO();
        int Id_Reserva = 0;
        Clients clientinserir = clientDAO.selectById(Integer.parseInt(idClientText.getText()));
        Taula taulainserir = taulaDAO.selectById(Integer.parseInt(idTaulaText.getText()));
        int numcomensals = Integer.parseInt(numComen.getText());
        LocalDate data = dataText.getValue();
        LocalTime horaArribada = LocalTime.parse(horaArribadaText.getText());
        // Calculem l'hora de sortida agafant l'hora d'entrada i sumant-hi 2 hores
        LocalTime horaSortida = horaArribada.plusHours(2);

        // Condicional per comprovar que la data seleccionada no és inferior a la data actual
        if (data.isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error a l'hora d'Inserir la Reserva");
            alert.setHeaderText("Error: No pots seleccionar una data anterior a la data actual.");
            alert.setContentText("Si us plau, selecciona una data vàlida.");
            alert.showAndWait();
            return;
        }

        // Condicional per comprovar que la taula que es vol reservar no està reservada a l'hora que es vol.
        List<Reserves> reservasTaula = reservaDAO.selectByTaulaIdAndDataAndInterval(taulainserir.getId_taula(), data,
                horaArribada, horaSortida);
        if (!reservasTaula.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error a l'hora d'Inserir la Reserva");
            alert.setHeaderText("Error: La taula ja està reservada en aquesta franja horària.");
            alert.setContentText("Si us plau, selecciona una altra taula o canvia l'interval de temps.");
            alert.showAndWait();
            return;
        }

        // Condicional per comprovar que el número de comensals no és superior a la capacitat de la taula.
        if (numcomensals > taulainserir.getCapacitat()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error a l'hora d'Inserir la Reserva");
            alert.setHeaderText("Error: La quantitat de comensals excedeix la capacitat de la taula.");
            alert.setContentText("Si us plau, selecciona una taula amb la capacitat suficient.");
            alert.showAndWait();
            return;
        }

        horaSortidaText.setText(horaSortida.toString());

        taulainserir.setEstat(true);
        taulaDAO.update(taulainserir);
        Reserves reserva = new Reserves(Id_Reserva, clientinserir, taulainserir, numcomensals, data, horaArribada,
                horaSortida, false);
        reservaDAO.insert(reserva);
        loadReservesData();
    }

    /**
     * Finalitza la reserva seleccionada marcant-la com a pendent.
     */
    @FXML
    private void finalitzarReserva() {
        r = tableViewReserves.getSelectionModel().getSelectedItem();
        if (r != null) {
            r.setPendent(true);

            reservaDAO.updatePendent(r);
            loadReservesData();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Finalitzar Reserva");
            alert.setContentText("Reserva finalitzada correctament.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Selecciona una reserva per finalitzar.");
            alert.showAndWait();
        }
    }

    /**
     * Anul·la la reserva seleccionada eliminant-la de la base de dades.
     */
    @FXML
    private void anularReserva() {
        r = tableViewReserves.getSelectionModel().getSelectedItem();
        if (r != null) {
            reservaDAO.delete(r);
            loadReservesData();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Anul·lar Reserva");
            alert.setContentText("Reserva anul·lada correctament.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Selecciona una reserva per anul·lar.");
            alert.showAndWait();
        }
    }

    /**
     * Mostra una finestra modal per seleccionar un client de la llista de clients.
     */
    @FXML
    private void showClientList() {
        ClientDAO clientDAO = new ClientDAO();
        ObservableList<Clients> clientsList = FXCollections.observableArrayList(clientDAO.selectAll());

        Stage clientStage = new Stage();
        clientStage.initModality(Modality.APPLICATION_MODAL);
        clientStage.setTitle("Seleccionar Client");

        TableView<Clients> clientTable = new TableView<>();
        TableColumn<Clients, Integer> columnIdClient = new TableColumn<>("ID");
        columnIdClient.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        TableColumn<Clients, String> columnNomClient = new TableColumn<>("Nom");
        columnNomClient.setCellValueFactory(new PropertyValueFactory<>("nom"));
        TableColumn<Clients, String> columnCognomClient = new TableColumn<>("Cognom");
        columnCognomClient.setCellValueFactory(new PropertyValueFactory<>("cognom"));
        TableColumn<Clients, String> columnTlfClient = new TableColumn<>("Telèfon");
        columnTlfClient.setCellValueFactory(new PropertyValueFactory<>("telefon"));

        clientTable.getColumns().addAll(columnIdClient, columnNomClient, columnCognomClient, columnTlfClient);
        clientTable.setItems(clientsList);

        clientTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Clients selectedClient = clientTable.getSelectionModel().getSelectedItem();
                if (selectedClient != null) {
                    idClientText.setText(String.valueOf(selectedClient.getId_client()));
                    clientStage.close();
                }
            }
        });

        VBox vbox = new VBox(clientTable);
        Scene scene = new Scene(vbox);
        clientStage.setScene(scene);
        clientStage.showAndWait();
    }

    /**
     * Mostra una finestra modal per seleccionar una taula de la llista de taules.
     */
    @FXML
    private void showTaulaList() {
        TaulaDAO taulaDAO = new TaulaDAO();
        List<Taula> taulaListShow = taulaDAO.selectAll();

        ObservableList<Taula> taulaList = FXCollections.observableArrayList(taulaListShow);

        Stage taulaStage = new Stage();
        taulaStage.initModality(Modality.APPLICATION_MODAL);
        taulaStage.setTitle("Seleccionar Taula");

        TableView<Taula> taulaTable = new TableView<>();
        TableColumn<Taula, Integer> columnIdTaula = new TableColumn<>("ID");
        columnIdTaula.setCellValueFactory(new PropertyValueFactory<>("id_taula"));
        TableColumn<Taula, Integer> columnCapacitat = new TableColumn<>("Capacitat");
        columnCapacitat.setCellValueFactory(new PropertyValueFactory<>("capacitat"));
        TableColumn<Taula, String> columnTipus = new TableColumn<>("Tipus Taula");
        columnTipus.setCellValueFactory(new PropertyValueFactory<>("tipusTaula"));

        taulaTable.getColumns().addAll(columnIdTaula, columnCapacitat, columnTipus);
        taulaTable.setItems(taulaList);

        taulaTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Taula selectedTaula = taulaTable.getSelectionModel().getSelectedItem();
                if (selectedTaula != null) {
                    idTaulaText.setText(String.valueOf(selectedTaula.getId_taula()));
                    taulaStage.close();
                }
            }
        });

        VBox vbox = new VBox(taulaTable);
        Scene scene = new Scene(vbox);
        taulaStage.setScene(scene);
        taulaStage.showAndWait();
    }

    /**
     * Neteja els camps de text del formulari.
     */
    @FXML
    private void clearFields() {
        idReservaText.clear();
        idTaulaText.clear();
        idClientText.clear();
        numComen.clear();
        dataText.setValue(null);
        horaArribadaText.clear();
        horaSortidaText.clear();
    }

    /**
     * Obre la finestra de gestió de clients.
     */
    @FXML
    private void gestioDeClients() {
        try {
            btnClients.getScene().getWindow().hide();
            Scene scenaGestioClients = new Scene(App.loadFXML("gestioclients"));
            Stage stage = new Stage();
            stage.setTitle("Gestió de Clients");
            stage.setResizable(true);
            stage.setScene(scenaGestioClients);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No es pot carregar la vista de la gestió de clients.");
            alert.showAndWait();
        }
    }

    /**
     * Tanca l'aplicació i torna a la finestra de login.
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
