package com.example.grid;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Scanner;

public class Grid {
    private int boxSize;
    String[] resultArray;
    private GridPane grid;
    private VBox vbox;
    private static final int GRID_SIZE = 28;
    private static final int CELL_SIZE = 20; // Size of each square in pixels
    private boolean drawing = false;

    public void load() {
        vbox = new VBox();
        vbox.setId("VBox-from-Grid-Class");
        HBox hBox = new HBox();grid = new GridPane();

        Button clearButton = new Button("clear");
        clearButton.setOnAction(e -> clear(e, grid));
        Button printButton = new Button("print");
        printButton.setOnAction(e -> print(grid));

        hBox.getChildren().add(printButton);
        hBox.getChildren().add(clearButton);
        vbox.getChildren().add(hBox);
        vbox.getChildren().add(grid);

        // Create the grid of rectangles
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE, Color.WHITE);
                rect.setStroke(Color.LIGHTGRAY);

                // Handle mouse events for each rectangle
                rect.setOnMousePressed(e -> handleMousePress(e));
                rect.setOnMouseReleased(e -> handleMouseRelease());
                rect.setOnMouseDragged(e -> handleMouseDragged(e, rect));

                // Add rectangle to grid
                grid.add(rect, col, row);
            }
        }

        // Enable mouse dragging on the whole grid
        grid.setOnMousePressed(e -> handleMousePress(e));
        grid.setOnMouseReleased(e -> drawing = false);
        grid.setOnMouseDragged(e -> handleMouseDragged(e, (Rectangle) e.getSource()));
        boxSize = vbox.getChildren().size();

        Application.switchScene(vbox);
    }

    // Handle mouse pressed event
    private void handleMousePress(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            drawing = true;
            // Check if it's over a rectangle and make it black
            Rectangle rect = (Rectangle) e.getSource();
            rect.setFill(Color.BLACK);
        }
    }

    // Handle mouse release event
    private void handleMouseRelease() {
        drawing = false;
    }

    // Handle mouse dragged event
    private void handleMouseDragged(MouseEvent e, Rectangle rect) {
        if (drawing && e.isPrimaryButtonDown()) {
            // Find the rectangle under the mouse
            //Rectangle rect = (Rectangle) e.getSource();
            rect.setFill(Color.BLACK);

            print(grid);
            NetResult netResult = new NetResult(resultArray, Main.net);
            if (vbox.getChildren().size() > boxSize) {
                vbox.getChildren().remove(boxSize);
                System.out.println("removing extra box");
            }
            vbox.getChildren().add(netResult.getBox());
            ((Stage) vbox.getScene().getWindow()).show();
        }
    }

    private void print(GridPane gridPane) {
        StringBuilder result = new StringBuilder();
        GridPane grid = gridPane;
        Scanner scanner = new Scanner(System.in);

        for (Node node : grid.getChildren()) {
            Rectangle rect = (Rectangle) node;
            if (rect.getFill().equals(Color.BLACK)) {
                result.append("130.0,");
            } else {
                result.append("0.0,");
            }
        }
        System.out.println(result);
        resultArray = new String[784];
        for (int i = 0; i < result.toString().split(",").length; i++) {
            resultArray[i] = result.toString().split(",")[i];
        }
    }

    private void clear(ActionEvent e, GridPane gridPane) {
        //GridPane grid = (GridPane) e.getSource();
        GridPane grid = gridPane;

        for (Node node : grid.getChildren()) {
            Rectangle rect = (Rectangle) node;
            rect.setFill(Color.WHITE);
        }
    }
}
