package com.example.grid;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {
    Grid grid = new Grid();
    @FXML Button loadButton;

    @FXML
    public void loadNetwork() {
        grid.load();
    }
}
