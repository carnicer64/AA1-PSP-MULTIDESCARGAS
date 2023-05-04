package com.svalero.downloader;

import com.svalero.downloader.controller.SplashScreenController;
import com.svalero.downloader.util.R;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        System.out.println(R.getUI("splashScreen.fxml"));
        loader.setLocation(R.getUI("splashScreen.fxml"));
        loader.setController(new SplashScreenController());

        AnchorPane anchorPane = loader.load();

        Scene scene = new Scene(anchorPane);
        stage.setTitle("Splash Screen");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();

    }

    @Override
    public void stop() throws Exception{
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }






}
