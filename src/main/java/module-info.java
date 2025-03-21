module com.example.grid {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.grid to javafx.fxml;
    exports com.example.grid;
}