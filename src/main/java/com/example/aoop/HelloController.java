package com.example.aoop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloController {
@FXML
public Stage stage;
public Scene scene;
public Parent root;


//  InputStream stream = new FileInputStream("C:\Users\Mehedi Hasan\IdeaProjects\AOOP\src\main\java\com\example\aoop\image\game.png");
//   Image image = new Image(stream);
//  ImageView imageView = new ImageView();
//           imageView.setImage(image);
    public void threeplayers(ActionEvent event) throws IOException {

            root = FXMLLoader.load(HelloApplication.class.getResource("FixtureThree.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        System.out.println("okk");

    }

    public void FivePlayers(ActionEvent actionEvent) {
    }

    public void FourPlayers(ActionEvent actionEvent) {
    }
}