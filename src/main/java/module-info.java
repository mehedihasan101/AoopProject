module com.example.aoop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.aoop to javafx.fxml;
    exports com.example.aoop;
}