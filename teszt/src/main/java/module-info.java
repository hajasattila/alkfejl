module com.example.teszt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.teszt to javafx.fxml;
    exports com.example.teszt;
}