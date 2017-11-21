import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ihm.AppController;

import java.io.IOException;
import java.net.URL;

public class PimpMyFridge extends Application {

    final String appName = "Pimp My Fridge";
    URL urlFxml;
    FXMLLoader loaderFXML;
    AppController controller;
    Parent root;
    Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        //Set application name.
        stage.setTitle(this.appName);

        //Chargement
        try {
            this.urlFxml = getClass().getResource("ihm/mainWindows.fxml");

            this.loaderFXML = new FXMLLoader(this.urlFxml);

            this.controller = loaderFXML.getController();


            this.root = loaderFXML.load();
            this.scene = new Scene(this.root, 1250, 700);
            stage.setScene(this.scene);
            stage.setResizable(false);

        }catch (IOException err){
            System.err.println("Erreur chargement du fichier .fxml : " + err.toString());
        }

        //Affichage de la fenetre
        stage.show();
    }
}