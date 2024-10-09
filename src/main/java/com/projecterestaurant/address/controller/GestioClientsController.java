/**
 * La classe GestioClientsController és responsable de gestionar les operacions de gestió de clients
 * en l'aplicació del restaurant. Això inclou carregar les dades dels clients, veure els detalls dels clients,
 * actualitzar, inserir i eliminar clients, i navegar a diferents vistes.
 * 
 * Interactua amb el ClientDAO per a les operacions de base de dades i gestiona els elements de la interfície d'usuari
 * definits en el fitxer FXML corresponent.
 */
package com.projecterestaurant.address.controller;

import java.io.IOException;
import com.projecterestaurant.App;
import com.projecterestaurant.address.dao.ClientDAO;
import com.projecterestaurant.address.model.Clients;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GestioClientsController {
    @FXML
    private Button btnReserves;
    @FXML
    private TableView<Clients> tableViewClients;
    @FXML
    private TableColumn<Clients, Integer> columnId;
    @FXML
    private TableColumn<Clients, String> columnName;
    @FXML
    private TableColumn<Clients, String> columnSurname;
    @FXML
    private TableColumn<Clients, Integer> columnTlf;

    @FXML
    private TextField idClientText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField surnameText;
    @FXML
    private TextField tlfText;
    @FXML
    private Button btnExit;

    private ObservableList<Clients> clientsList;

    private Clients c;

    ClientDAO clientDAO = new ClientDAO();

    /**
     * Inicialitza el controlador. Configura les columnes de la taula i carrega les dades dels clients.
     */
    @FXML
    public void initialize() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnSurname.setCellValueFactory(new PropertyValueFactory<>("cognom"));
        columnTlf.setCellValueFactory(new PropertyValueFactory<>("telefon"));

        loadClientsData();
    }

    /**
     * Carrega les dades dels clients des de la base de dades i les mostra a la taula.
     */
    private void loadClientsData() {
        clientsList = FXCollections.observableArrayList(clientDAO.selectAll());
        this.tableViewClients.setItems(clientsList);
    }

    /**
     * Mostra els detalls del client seleccionat en els camps de text.
     * 
     * @param event Esdeveniment de clic del ratolí.
     */
    @FXML
    private void veureDetallsTaula(MouseEvent event) {
        c = tableViewClients.getSelectionModel().getSelectedItem();
        if (c != null) {
            idClientText.setText(String.valueOf(c.getId_client()));
            nameText.setText(String.valueOf(c.getNom()));
            surnameText.setText(String.valueOf(c.getCognom()));
            tlfText.setText(String.valueOf(c.getTelefon()));
        }
    }

    /**
     * Actualitza el client amb les dades dels camps de text i refresca la taula.
     */
    @FXML
    private void actualizarClient() {
        int Id_Client = Integer.parseInt(idClientText.getText());
        String Nom_client = nameText.getText();
        String Cognom_client = surnameText.getText();
        int Tlf_Client = Integer.parseInt(tlfText.getText());

        Clients clientactualitzar = new Clients(Id_Client, Nom_client, Cognom_client, Tlf_Client);
        clientDAO.update(clientactualitzar);
        loadClientsData();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Actualitzar Client");
        alert.setContentText("Client Actualitzat Correctament");
        alert.showAndWait();
    }

    /**
     * Insereix un nou client amb les dades dels camps de text i refresca la taula.
     */
    @FXML
    private void inserirTaula() {
        int Id_Client = 0;
        String Nom_client = nameText.getText();
        String Cognom_client = surnameText.getText();
        int Tlf_Client = Integer.parseInt(tlfText.getText());

        Clients clientInserir = new Clients(Id_Client, Nom_client, Cognom_client, Tlf_Client);
        clientDAO.insert(clientInserir);
        loadClientsData();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Client Inserit");
        alert.setContentText("Client Inserit Correctament");
        alert.showAndWait();
    }

    /**
     * Elimina el client seleccionat si no té reserves associades i refresca la taula.
     */
    @FXML
    private void eliminarTaula() {
        int Id_Client = Integer.parseInt(idClientText.getText());

        // Fem un condicional per comprovar si el client té alguna reserva feta
        if (clientDAO.clientHasReservation(Id_Client)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error a l'hora d'eliminar el client");
            alert.setHeaderText("No es pot eliminar el client");
            alert.setContentText("El client té reserves associades i no es pot eliminar.");
            alert.showAndWait();
            return;
        }

        String Nom_client = nameText.getText();
        String Cognom_client = surnameText.getText();
        int Tlf_Client = Integer.parseInt(tlfText.getText());

        Clients clientEliminar = new Clients(Id_Client, Nom_client, Cognom_client, Tlf_Client);
        clientDAO.delete(clientEliminar);
        loadClientsData();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Client Eliminat");
        alert.setContentText("Client Eliminat Correctament");
        alert.showAndWait();
    }

    /**
     * Neteja els camps de text.
     */
    @FXML
    private void clearFields() {
        idClientText.clear();
        nameText.clear();
        surnameText.clear();
        tlfText.clear();
    }

    /**
     * Navega a la vista de gestió de reserves.
     */
    @FXML
    private void gestioDeReserves() {
        try {
            btnReserves.getScene().getWindow().hide();
            Scene scenaGestioReserves = new Scene(App.loadFXML("gestioreserves"));
            Stage stage = new Stage();
            stage.setTitle("Gestio de Reserves");
            stage.setResizable(false);
            stage.setScene(scenaGestioReserves);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No es pot carregar la vista de reserves.");
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
