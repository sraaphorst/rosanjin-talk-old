package com.zetcode;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

// Application is the entry point for the application.
// Stage is the top-level container, i.e. main window.
// Scene is a container for visual content of Stage as a graph called the Scene graph.
public class FirstEx extends Application {

    @Override
    public void start(Stage stage) {

        initUI(stage);
    }

    // User interface.
    private void initUI(Stage stage) {
        stage.setTitle("Simple application");

        // StackPane is a simple layout manager.
        var root = new StackPane();
        var scene = new Scene(root, 300, 250);
        stage.setScene(scene);

        var children = root.getChildren();
        var lbl = new Label("Simple JavaFX application.");
        lbl.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 20));
        children.add(lbl);

        stage.show();
    }

    // Typically not needed: only used as fallback when JavaFX launching is not working.
    public static void main(final String[] args) {
        launch(args);
    }
}