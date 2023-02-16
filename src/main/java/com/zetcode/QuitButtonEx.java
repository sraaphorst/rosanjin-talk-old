package com.zetcode;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

final public class QuitButtonEx extends Application {
    @Override
    public void start(final Stage stage) {
        initUI(stage);
    }

    private void initUI(final Stage stage) {
        final var root = new HBox();
        root.setPadding(new Insets(25));
        final var children = root.getChildren();

        final var btn = new Button("Quit");
        btn.setOnAction((final ActionEvent e) -> Platform.exit());

        // Note that if the following line is added, it overrides the previous EventHandler.
//        btn.setOnAction((final ActionEvent e) -> System.out.println("1"));
        // This, however, works, and both handlers are executed.
        btn.addEventHandler(ActionEvent.ACTION, (final ActionEvent e) -> System.out.println("2"));
        children.add(btn);

        final var scene = new Scene(root, 280, 200);

        stage.setTitle("Quit button");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
