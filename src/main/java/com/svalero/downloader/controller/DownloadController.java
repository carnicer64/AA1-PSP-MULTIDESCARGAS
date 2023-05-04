package com.svalero.downloader.controller;

import com.svalero.downloader.task.DownloadTask;
import javafx.concurrent.Worker.State;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;


public class DownloadController implements Initializable {

    public TextField tfUrl;
    public Label lbName;
    public Label lbStatus;
    public Label lbSize;
    public Label lbProgress;
    public ProgressBar pbProgress;
    private String urlText;
    private DirectoryChooser directoryChooser = new DirectoryChooser();
    private JSONArray message;
    private DownloadTask downloadTask;

    Logger logger = LogManager.getLogger(DownloadController.class);

    public DownloadController(String urlText) {
        logger.info("Descarga " + urlText + " creada");
        this.urlText = urlText;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Sacamos el nombre del archivo
        String name = this.urlText.substring(this.urlText.lastIndexOf("/") + 1);
        lbName.setText(name);

        tfUrl.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) tfUrl.getScene().getWindow();
                File file = directoryChooser.showDialog(stage);
                if (file != null) {
                    tfUrl.setText(file.getAbsolutePath());
                }
            }
        });
    }

    @FXML
    public void startDownload(ActionEvent event) {
        try {

            File file = new File(tfUrl.getText(), lbName.getText());
            if (file == null)
                return;

            downloadTask = new DownloadTask(urlText, file);

            pbProgress.progressProperty().unbind();
            pbProgress.progressProperty().bind(downloadTask.progressProperty());

            downloadTask.stateProperty().addListener((observableValue, oldState, newState) -> {

                if (newState == Worker.State.SUCCEEDED) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("La descarga ha terminado");
                    alert.show();
                } else if (newState == Worker.State.CANCELLED) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("La descarga ha sido cancelada");
                    alert.show();

                } else if (newState == Worker.State.FAILED) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("La descarga ha fallado");
                    alert.show();
                }

            });

            downloadTask.messageProperty().addListener((observableValue, oldValue, newValue) -> {
                message = new JSONArray(newValue);
                lbSize.setText(message.get(0).toString());
                lbProgress.setText(message.get(1).toString());
                lbStatus.setText(message.get(2).toString());


            });


            new Thread(downloadTask).start();


        } catch (MalformedURLException murle) {
            murle.printStackTrace();
            logger.error("URL mal formada", murle.fillInStackTrace());
        }

    }

    public void stop() {
        if (downloadTask != null)
            downloadTask.cancel();
        lbStatus.setText ("Cancelado");
    }

    @FXML
    public void cancelDownload(ActionEvent event) { stop();}
}
