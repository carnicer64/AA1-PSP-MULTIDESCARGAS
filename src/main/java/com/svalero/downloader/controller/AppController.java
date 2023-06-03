package com.svalero.downloader.controller;

import com.svalero.downloader.util.R;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AppController {

    public TabPane tpDown;
    public Button btDown;
    public TextField tfUrl;

    private Map<String, DownloadController> allDownloads;

    public AppController() {
        allDownloads = new HashMap<>();
    }

    //Metodo que se activa al inicial descarga

    @FXML
    public void startDownload(ActionEvent event) {
        String urlText = tfUrl.getText();
        tfUrl.clear();
        tfUrl.requestFocus();
        launchDownload(urlText);
    }

    @FXML
    public void showLog(ActionEvent actionEvent) throws IOException{
        try {
            File file = new File("AADownloader.log");
            Desktop.getDesktop().open(file);
        } catch (IOException ioe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error 1111");
            alert.show();
        }
    }

    @FXML
    public void downloadFromFile() {
        try {

            // FileChooser permite escoger un fichero.
            FileChooser fileChooser = new FileChooser();
            File dlcFile = fileChooser.showOpenDialog(tfUrl.getScene().getWindow());

            Scanner reader = new Scanner(dlcFile);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                System.out.println(data);
                launchDownload(data);
            }
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void launchDownload(String url) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(R.getUI("downloadPage.fxml"));

            DownloadController downloadController = new DownloadController(url);
            loader.setController(downloadController);
            VBox downloadBox = loader.load();

            String fileName = url.substring(url.lastIndexOf("/") + 1);
            tpDown.getTabs().add(new Tab(fileName, downloadBox));

            allDownloads.put(url, downloadController);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void stopAllDownloads(ActionEvent actionEvent) {
        for (DownloadController downloadController : allDownloads.values()) {
            downloadController.stop();
        }
    }


}
