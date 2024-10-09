package com.projecterestaurant.address.controller;

import java.io.IOException;

import com.projecterestaurant.App;
import com.projecterestaurant.address.dao.TaulaDAO;
import com.projecterestaurant.address.enums.TipusTaula;
import com.projecterestaurant.address.model.Taula;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controlador per a la gestió de taules.
 */
public class GestioTaulaController {
    @FXML
    private TableView<Taula> tableViewTaules;
    @FXML
    private TableColumn<Taula, Integer> columnId;
    @FXML
    private TableColumn<Taula, Integer> columnCapacitat;
    @FXML
    private TableColumn<Taula, String> columnTipus;

    @FXML
    private TextField idTaulaText;
    @FXML
    private TextField capacitatText;
    @FXML
    private ComboBox<TipusTaula> tipusText;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnEstadistiques;

    private ObservableList<Taula> taulaList;

    private Taula t;

    TaulaDAO taulaDAO = new TaulaDAO();

    /**
     * Inicialitza el controlador.
     */
    @FXML
    public void initialize() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id_taula"));
        columnCapacitat.setCellValueFactory(new PropertyValueFactory<>("capacitat"));
        columnTipus.setCellValueFactory(new PropertyValueFactory<>("tipusTaula"));

        // Inicialitza el ComboBox amb els valors del enum TipusTaula
        tipusText.setItems(FXCollections.observableArrayList(TipusTaula.values()));
        loadTaulaData();
    }

    /**
     * Carrega les dades de les taules a la taula.
     */
    private void loadTaulaData() {
        taulaList = FXCollections.observableArrayList(taulaDAO.selectAll());
        this.tableViewTaules.setItems(taulaList);
    }

    /**
     * Maneja l'esdeveniment de clicar per veure els detalls d'una taula.
     * 
     * @param event L'esdeveniment de ratolí
     */
    @FXML
    private void veureDetallsTaula(MouseEvent event) {
        t = tableViewTaules.getSelectionModel().getSelectedItem();
        if (t != null) {
            idTaulaText.setText(String.valueOf(t.getId_taula()));
            capacitatText.setText(String.valueOf(t.getCapacitat()));
            tipusText.setValue(t.getTipusTaula());
        }
    }

    /**
     * Actualitza la informació d'una taula.
     */
    @FXML
    private void actualizarTaula() {
        int Id_Taula = Integer.parseInt(idTaulaText.getText());
        int Capacitat_Taula = Integer.parseInt(capacitatText.getText());
        TipusTaula taula_tipus = tipusText.getValue();
        Boolean estat_taula = true;

        Taula taulaActualitar = new Taula(Id_Taula, Capacitat_Taula, taula_tipus, estat_taula);
        taulaDAO.update(taulaActualitar);
        loadTaulaData();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Actualitzar Taula");
        alert.setContentText("Taula Actualitzada Correctament");
        alert.showAndWait();
    }

    /**
     * Insereix una nova taula.
     */
    @FXML
    private void inserirTaula() {
        int Id_Taula = 0;
        int Capacitat_Taula = Integer.parseInt(capacitatText.getText());
        TipusTaula taula_tipus = tipusText.getValue();
        Boolean estat_taula = false;

        Taula taulaInserir = new Taula(Id_Taula, Capacitat_Taula, taula_tipus, estat_taula);
        taulaDAO.insert(taulaInserir);
        loadTaulaData();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Taula Inserida");
        alert.setContentText("Taula Inserida Correctament");
        alert.showAndWait();
    }

    /**
     * Elimina una taula existent.
     */
    @FXML
    private void eliminarTaula() {
        int Id_Taula = Integer.parseInt(idTaulaText.getText());
        int Capacitat_Taula = Integer.parseInt(capacitatText.getText());
        TipusTaula taula_tipus = tipusText.getValue();
        Boolean estat_taula = false;

        Taula taulaEliminar = new Taula(Id_Taula, Capacitat_Taula, taula_tipus, estat_taula);
        taulaDAO.delete(taulaEliminar);
        loadTaulaData();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Taula Eliminada");
        alert.setContentText("Taula Eliminada Correctament");
        alert.showAndWait();
    }

    /**
     * Gestiona l'acció de mostrar les estadístiques.
     */
    @FXML
    private void gestioEstadistiques() {
        try {
            btnEstadistiques.getScene().getWindow().hide();
            Scene scenaGestioEstadistiques = new Scene(App.loadFXML("estadistiquesview"));
            Stage stage = new Stage();
            stage.setTitle("Estadístiques de reserves");
            stage.setResizable(false);
            stage.setScene(scenaGestioEstadistiques);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No es pot carregar la vista d'estadístiques.");
            alert.showAndWait();
        }
    }

    /**
     * Esborra els camps de text.
     */
    @FXML
    private void clearFields() {
        idTaulaText.clear();
        capacitatText.clear();
        tipusText.setValue(null);
    }

    /**
     * Gestiona l'acció de sortir de l'aplicació.
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

        }
    }
}