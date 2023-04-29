package com.example.aoop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FixtureThreeController {
    @FXML
    public Stage stage ;
    public Scene scene;
    public Parent root;



    public void Back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(HelloApplication.class.getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void PongGame(ActionEvent event) {
//        StartingClass pong=new StartingClass();
//        pong.setVisible(true);
    }
}
