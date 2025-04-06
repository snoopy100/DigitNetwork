package com.example.grid;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    static Scene scene;
    FXMLLoader loader;
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Drawing Grid");
        loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }

    public static void switchScene(Parent sc) {
        Platform.runLater(() -> {
            scene.setRoot(sc);
            ((Stage) scene.getWindow()).show();
            System.out.println("Scene ID : " + scene.getRoot().getId());
        });
    }
}
