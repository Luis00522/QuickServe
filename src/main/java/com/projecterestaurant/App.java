/**
 * La classe App és l'entrada principal de l'aplicació del restaurant.
 * S'encarrega d'inicialitzar i mostrar l'escenari principal de l'aplicació
 * utilitzant JavaFX.
 */
package com.projecterestaurant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    /**
     * Inicia l'aplicació JavaFX.
     * 
     * @param stage L'escenari principal de l'aplicació.
     * @throws IOException Si hi ha un error en carregar el fitxer FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("loginview"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Estableix l'arrel de l'escena actual amb el fitxer FXML especificat.
     * 
     * @param fxml El nom del fitxer FXML a carregar.
     * @throws IOException Si hi ha un error en carregar el fitxer FXML.
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Carrega un fitxer FXML i retorna el seu contingut com a objecte Parent.
     * 
     * @param fxml El nom del fitxer FXML a carregar.
     * @return El contingut carregat del fitxer FXML com a objecte Parent.
     * @throws IOException Si hi ha un error en carregar el fitxer FXML.
     */
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
            App.class.getResource("/com/projecterestaurant/address/view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * El punt d'entrada principal de l'aplicació.
     * 
     * @param args Els arguments de la línia de comandes.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
