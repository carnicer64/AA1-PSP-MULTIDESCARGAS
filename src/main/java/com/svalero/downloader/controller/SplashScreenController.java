package com.svalero.downloader.controller;

import com.svalero.downloader.util.R;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable {

    @FXML
    private ImageView iView1;

    @FXML
    private AnchorPane aPane1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { new SplashScreen().start();}

    class SplashScreen extends Thread {

        public void run() {
            //Buscar manera de añadir Imagen
            try {
                Thread.sleep(3000);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        FXMLLoader loader = new FXMLLoader();
                        System.out.println(R.getUI("welcomePage.fxml"));
                        loader.setLocation(R.getUI("welcomePage.fxml"));
                        loader.setController(new AppController());

                        VBox vBox = null;
                        try {
                            vBox = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Scene scene = new Scene(vBox);
                        Stage stage = new Stage();
                        stage.setTitle("welcomePage");
                        stage.setScene(scene);
                        stage.show();


                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}