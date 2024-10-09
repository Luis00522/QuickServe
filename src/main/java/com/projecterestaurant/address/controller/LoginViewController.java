package com.projecterestaurant.address.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.projecterestaurant.App;
import com.projecterestaurant.address.dao.UsuariDAO;
import com.projecterestaurant.address.model.Usuari;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador per a la vista de login.
 */
public class LoginViewController {

    @FXML
    private TextField txtUsuari;
    @FXML
    private TextField txtContrasenya;
    @FXML
    private Button btnAcceptar;
    @FXML
    private Button btn;
    private Set<Usuari> usuaris;
    private Usuari usuariLogin;
    private Stage stage;

    private UsuariDAO ud;

    /**
     * Constructor de la classe.
     */
    public LoginViewController() {
        ud = new UsuariDAO();
        usuaris = new HashSet<Usuari>(ud.selectAll());
    }

    /**
     * Defineix l'escenari.
     * 
     * @param stage L'escenari
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Gestiona l'acció de l'acceptació del login.
     * 
     * @param event L'esdeveniment d'acció
     */
    @FXML
    private void acceptar(ActionEvent event) {

        // Condicional per comprovar que les dades que inserim concideixen amb els
        // usuaris que tenim a la base de dades
        if (!txtUsuari.getText().isEmpty() && !txtContrasenya.getText().isEmpty()) {
            usuariLogin = new Usuari(txtUsuari.getText(), txtContrasenya.getText());
            if (validarUsuari(usuariLogin)) {
                System.out.println("Funciona");
                carregarAppPrincipal(usuariLogin);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setContentText("Usuari inexistent.");
                alert.showAndWait();

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Usuari i/o contrasenya incorrectes...");
            alert.showAndWait();
        }
    }

    /**
     * Valida les dades d'un usuari.
     * 
     * @param usuari L'usuari a validar
     * @return True si l'usuari és vàlid, false altrament
     */
    private boolean validarUsuari(Usuari usuari) {
        if (usuaris.contains(usuari)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Carrega l'aplicació principal depenent del rol de l'usuari.
     * 
     * @param usuari L'usuari logat
     */
    private void carregarAppPrincipal(Usuari usuari) {
        try {
            System.out.println("Carregar app principal");
            btnAcceptar.getScene().getWindow().hide();
            Usuari usuari1 = ud.selectByNomAndPassword(usuari.getNom(), usuari.getPassword());
            // Condicional per obrir una vista o una altre depenen del rol del usuari
            if (usuari1.getSupervisor() == 1) {

                Scene scenaGestiotaula = new Scene(App.loadFXML("gestiotaula"));
                Stage stage = new Stage();
                stage.setTitle("Gestio de Taules");
                stage.setResizable(false);
                stage.setScene(scenaGestiotaula);
                stage.show();

            } else {
                Scene scenaGestioreserva = new Scene(App.loadFXML("gestioclients"));
                Stage stage = new Stage();
                stage.setTitle("Gestio de Clients");
                stage.setResizable(false);
                stage.setScene(scenaGestioreserva);
                stage.show();
            }

        } catch (IOException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
