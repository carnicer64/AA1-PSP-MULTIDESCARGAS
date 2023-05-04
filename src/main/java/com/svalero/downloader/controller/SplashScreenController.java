package com.svalero.downloader.controller;

import com.svalero.downloader.util.R;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable {

    @FXML
    private ImageView iView1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Buscar manera de añadir Imagen
        FadeTransition transition = new FadeTransition(Duration.millis(3000), iView1);
        transition.setFromValue(1.0);
        transition.setToValue(1.0);
        transition.play();

        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(R.getUI("downloadPage.fxml"));
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
    }

}