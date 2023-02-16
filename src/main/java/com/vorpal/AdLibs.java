package com.vorpal;

// By Sebastian Raaphorst, 2023.

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdLibs extends Application {
    @Override
    public void start(final Stage stage) {
        final var inputPanel =  new InputPanel();
        final var scene = new Scene(inputPanel, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Substitutions");
        stage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
