package com.example.aoop;

import com.example.aoop.PongGame.Test;
import com.example.aoop.image.TicTacToe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayController {
    @FXML
    public Stage stage ;
    public Scene scene;
    public Parent root;

    public void pingpong(ActionEvent event)  {
        Test t=new Test();
        t.setVisible(true);
    }

    public void frogger(ActionEvent event) {

    }

    public void tictactoe(ActionEvent event) {
        TicTacToe tictac=new TicTacToe();
        tictac.setVisible(true);
    }

    public void Back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(HelloApplication.class.getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
