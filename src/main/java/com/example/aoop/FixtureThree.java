package com.example.aoop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class FixtureThree extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FixtureThree.class.getResource("FixtureThree.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
      //  stage.setTitle("games");
        stage.setScene(scene);
        stage.show();
    }
}
