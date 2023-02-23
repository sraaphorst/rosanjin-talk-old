package com.vorpal.rosanjintalk.ui;

// By Sebastian Raaphorst, 2023.

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdLibs extends Application {
    @Override
    public void start(final Stage stage) {
//        final var root = new CreatePanel();
        final var root = new SplashScreen();
        final var scene = new Scene(root, 400, 500);
        stage.setScene(scene);

        stage.setScene(scene);
        stage.setTitle("Substitutions");
        stage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
