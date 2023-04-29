package com.example.aoop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class FixtureTwoController implements Initializable {
    @FXML
    public Stage stage ;
    public Scene scene;
    public Parent root;
@FXML
    Button playbutton;
@FXML
    TextArea textarea;
@FXML
    TextField textfield;
@FXML
Button button1;
@FXML
Button button2;
@FXML
Button button3;
@FXML
Button button4;
@FXML
Button button5;
@FXML
Button button6;
@FXML
Button button7;
@FXML
Button button8;
@FXML
Button button9;
Button newbutton;
String id;
@FXML
  private Text textLabel;
    private int playerTurn = 0;

    ArrayList<Button> buttons;
    Boolean isConnected = false;
    BufferedReader reader;
    BufferedWriter writter;

    public void FixtureTwoBack(ActionEvent event)throws IOException {
        root = FXMLLoader.load(HelloApplication.class.getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
@FXML
    public void play(ActionEvent event) {
           if(!isConnected){
               String inputName=textfield.getText();
                 textfield.clear();
                 if(inputName ==null || inputName.length()==0){
                     textarea.setText("Sorry!, Input Valid name \n");
                      return;
                 }
                     try{
                         Socket CLsc=new Socket("Localhost", 700);

                         OutputStreamWriter out = new OutputStreamWriter(CLsc.getOutputStream());
                         writter = new BufferedWriter(out);
                         writter.write(inputName+"\n");
                         writter.flush();

                         InputStreamReader in=new InputStreamReader(CLsc.getInputStream());
                         reader =new BufferedReader(in);

                       Thread serverListener=new Thread(){
                           @Override
                           public void run(){
                               while (true){
                                   try{
                                       String data=reader.readLine()+"\n";
                                       System.out.println(data);
                                       if(data.equals("X")){
                                            switch (id){
                                                  case "button1": button1=new Button("X");
                                                      System.out.println(id);
                                                  break;
                                                  case "button2":  button2=new Button("X");
                                                      System.out.println(id);
                                                  break;
                                                  case "button3":  button3=new Button("X");
                                                      System.out.println(id);
                                                      break;
                                                  case "button4":  button4=new Button("X");
                                                      System.out.println(id);
                                                      break;
                                                  case "button5":  button5=new Button("X");
                                                      System.out.println(id);
                                                      break;
                                                  case "button6":  button6=new Button("X");
                                                      System.out.println(id);
                                                      break;
                                                  case "button7":  button7=new Button("X");
                                                      break;
                                                  case "button8": button8=new Button("X");
                                                      break;
                                                  case "button9": button9=new Button("X");
                                                      break;

                                              }

                                       }else if(data.equals("O")) {

                                           switch (id){
                                                   case "button1": button1=new Button("O");
                                                       System.out.println(id);
                                                       break;
                                                   case "button2": button2=new Button("O");
                                                       System.out.println(id);
                                                       break;
                                                   case "button3": button3=new Button("O");
                                                       System.out.println(id);
                                                       break;
                                                   case "button4": button4=new Button("O");
                                                       System.out.println(id);
                                                       break;
                                                   case "button5": button5=new Button("O");
                                                       System.out.println(id);
                                                       break;
                                                   case "button6": button6=new Button("O");
                                                       System.out.println(id);
                                                       break;
                                                   case "button7": button7=new Button("O");
                                                       break;
                                                   case "button8": button8=new Button("O");
                                                       break;
                                                   case "button9": button9=new Button("O");
                                                       break;
                                              }
                                       }else {
                                           textarea.appendText(data);

                                       }
                                   }catch (SocketException e){
                                       textarea.appendText("Connection lost with server\n");
                                       break;
                                   } catch (Exception e){
                                       System.out.println(e);
                                   }
                               }
                           }
                       };
                         serverListener.start();
                         textfield.setPromptText("Write your massage");
                         playbutton.setText("Send");
                         textarea.appendText("Connection Establish with server\n");
                         isConnected=true;
                     }catch (Exception e){
                  e.printStackTrace();
                     }
           }else {
               try{
                   String msg=textfield.getText();
                   textfield.clear();
                   if(msg==null || msg.length()==0){
                       return;
                   }
                   writter.write(msg+"\n");
                   writter.flush();
               }catch (Exception e){
                   e.printStackTrace();
               }

           }

    }

    public void Restart(ActionEvent event) {
        buttons.forEach(this::resetButton);
        textLabel.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttons = new ArrayList<>(Arrays.asList(button1,button2,button3,button4,button5,button6,button7,button8,button9));

        buttons.forEach(button ->{
            setupButton(button);
            button.setFocusTraversable(false);
        });
    }



    public void resetButton(Button button){
        button.setDisable(false);
        button.setText("");
    }

    private void setupButton(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            Button  newbutton= (Button) mouseEvent.getSource();
             id = newbutton.getId();
            //System.out.println(id);
            setPlayerSymbol(button);
            button.setDisable(true);
            checkIfGameIsOver();
        });
    }

    public void setPlayerSymbol(Button button){
        if(playerTurn % 2 == 0){
            try{
                writter.write("X"+"\n");
                writter.flush();
            }catch (Exception e){
                e.printStackTrace();
            }

          // button.setText("X");
            playerTurn = 1;
        } else{
            try{
                writter.write("O"+"\n");
                writter.flush();
            }catch (Exception e){
                e.printStackTrace();
            }
           // button.setText("O");
            playerTurn = 0;
        }
    }

    public void checkIfGameIsOver(){
        for (int a = 0; a < 8; a++) {
            String line = switch (a) {
                case 0 -> button1.getText() + button2.getText() + button3.getText();
                case 1 -> button4.getText() + button5.getText() + button6.getText();
                case 2 -> button7.getText() + button8.getText() + button9.getText();
                case 3 -> button1.getText() + button5.getText() + button9.getText();
                case 4 -> button3.getText() + button5.getText() + button7.getText();
                case 5 -> button1.getText() + button4.getText() + button7.getText();
                case 6 -> button2.getText() + button5.getText() + button8.getText();
                case 7 -> button3.getText() + button6.getText() + button9.getText();
                default -> null;
            };

            //X winner
            if (line.equals("XXX")) {
                textLabel.setText("Player X won!");
            }

            //O winner
            else if (line.equals("OOO")) {
                textLabel.setText("Player O won!");
            }
        }
    }
}
