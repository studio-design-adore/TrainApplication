module com.example.trainapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.trainapplication to javafx.fxml;
    exports com.example.trainapplication;
}