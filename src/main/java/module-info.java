module com.example.grid {
    requires javafx.controls;
    requires javafx.fxml;
    requires neuroph;
    requires encog.core;


    opens com.example.grid to javafx.fxml;
    exports com.example.grid;
}