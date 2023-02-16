package com.zetcode;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

final public class ButtonExamples extends Application {
    @Override
    public void start(final Stage stage) {
        initUI(stage);
    }

    private void initUI(final Stage stage) {
//        // If we turn mnemonic parsing on for the label (not on by default) and set the
//        // label for a node, then that node should grab the focus when the mnemonic is pressed.
//        final var label1 = new Label("_Activate me:");
//        label1.setMnemonicParsing(true);
//        label1.setStyle("-fx-text-alignment: left; -fx-padding: 5px");
//        final var label1Tooltip = new Tooltip("I am a BANANA!");
//        Tooltip.install(label1, label1Tooltip);
//
//        // Mnemonic parsing is turned on by default for buttons but doesn't work on Mac.
//        final var button1 = new Button();
//        button1.setText("_Byebye!");
//        button1.addEventHandler(ActionEvent.ACTION, (final ActionEvent e) -> System.out.println("Quitting..."));
//        button1.setOnAction((final ActionEvent e) -> Platform.exit());
//        final var button1Tooltip = new Tooltip("Quitter!");
//        Tooltip.install(button1, button1Tooltip);
//        label1.setLabelFor(button1);
//
//        final var label2 = new Label("_Clickety: ");
//        label2.setMnemonicParsing(true);
//        label2.setStyle("-fx-text-alignment: left; -fx-padding: 5px");
//        final var label2Tooltip = new Tooltip("I dunno.");
//        Tooltip.install(label2, label2Tooltip);
//
//        final var button2 = new Button("_Da printer button");
//        button2.setOnAction((final ActionEvent e) -> System.out.println("PRINTING!"));
//        final var button2Tooltip = new Tooltip("The printer button. Beware.");
//        Tooltip.install(button2, button2Tooltip);
//        label2.setLabelFor(button2);
//
//        final var tlayout = new GridPane();
//        ColumnConstraints column0 = new ColumnConstraints();
//        column0.setPercentWidth(50);
//        tlayout.addColumn(0, label1, label2);
//        ColumnConstraints column1 = new ColumnConstraints();
//        column1.setPercentWidth(50);
//        tlayout.addColumn(1, button1, button2);
//
//        final var hbox1 = new HBox(label1, button1);
//        hbox1.setPadding(new Insets(20));
//
//        final var hbox2 = new HBox(label2, button2);
//        hbox2.setPadding(new Insets(20));

//        final var root = new VBox(hbox1, hbox2);
        final var inputPane = createInputPane();
        final var scene = new Scene(inputPane, 500, 500);

        // Mnemonics not working are a known issue on Mac.
        // Use accelerators instead.
        // This has to be assigned after the button has been assigned to a scene.
//        label1.getScene().getAccelerators().put(
//                new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN),
//                button1::requestFocus);
//        button1.getScene().getAccelerators().put(
//                new KeyCodeCombination(KeyCode.B, KeyCombination.SHORTCUT_DOWN),
//                button1::fire);
//        label2.getScene().getAccelerators().put(
//                new KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN),
//                button2::requestFocus);
//        button2.getScene().getAccelerators().put(
//                new KeyCodeCombination(KeyCode.D, KeyCombination.SHORTCUT_DOWN),
//                button2::fire);

        stage.setTitle("Test");
        stage.setScene(scene);
        stage.show();
    }

    private static class Row {
        private static int rowIdx = 0;

        final CheckBox cb = new CheckBox();
        final TextField substitution = new TextField();
        final TextField value = new TextField();

        public Row() {
            cb.setSelected(false);
            cb.setFocusTraversable(false);
//            cb.setStyle("-fx-padding: 5px");
            substitution.setText("{" + (++rowIdx) + "}");
            substitution.setStyle("-fx-alignment: center; -fx-text-alignment: right");
            substitution.setDisable(true);
            substitution.setFocusTraversable(false);
//            value.setStyle("-fx-padding: 5px");
        }
    }

    final ArrayList<Row> rows = new ArrayList<>();

    private Pane createInputPane() {
        final var gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(10));
        final var c0 = new ColumnConstraints();
        c0.setPercentWidth(5);
        final var c1 = new ColumnConstraints();
        c1.setPercentWidth(15);
        final var c2 = new ColumnConstraints();
        c2.setPercentWidth(80);
//        c2.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(c0, c1, c2);

        final var substitutionLabel = new Label("Substitution");
        substitutionLabel.setStyle("-fx-text-alignment: center");
        gridPane.add(substitutionLabel, 1, 0);
        final var descriptionLabel = new Label("Description");
        descriptionLabel.setStyle("-fx-text-alignment: center");
        gridPane.add(descriptionLabel, 2, 0);

        final var addButton = new Button("Add Row");
        final var deleteButton = new Button("Delete Row");

        final var hbox = new HBox(addButton, deleteButton);
        hbox.setPadding(new Insets(25));
        hbox.setStyle("-fx-alignment: center");
        hbox.setSpacing(100);

        final var vbox = new VBox(gridPane, hbox);

        addButton.setOnAction((final ActionEvent e) -> {
            final var row = new Row();
            rows.add(row);
            gridPane.addRow(gridPane.getRowCount(), row.cb, row.substitution, row.value);
            VBox.setVgrow(vbox, Priority.ALWAYS);
        });
        addButton.fire();
        deleteButton.setOnAction((final ActionEvent e) -> {
            rows.stream()
                    .filter(row -> row.cb.isSelected())
                    .forEach(row -> gridPane.getChildren().removeAll(row.cb, row.substitution, row.value));
        });

        return vbox;
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
