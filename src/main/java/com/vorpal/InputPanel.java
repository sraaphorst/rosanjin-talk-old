package com.vorpal;

// By Sebastian Raaphorst, 2023.

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * An input panel to manage the substitution information for the main corpus.
 */
final public class InputPanel extends VBox {
    // Holds the rows, which contain the UI elements for each row.
    private final List<Row> rows = new ArrayList<>();

    /**
     * A row in the GridPane that contains the substitution information.
     */

    // Number of rows that have been added. Cannot store in Row in Java 11.
    private static int rowIdx = 0;

    final EventHandler<ActionEvent> deleteCheck = (final ActionEvent e) -> processDeleteButton();

    final private class Row {
        final CheckBox cb = new CheckBox();
        final TextField substitution = new TextField();
        final TextField value = new TextField();

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Row)) return false;
            Row row = (Row) o;
            return cb.equals(row.cb) && substitution.equals(row.substitution) && value.equals(row.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(cb, substitution, value);
        }

        public Row() {
            // We check the delete button when the checkboxes are checked to see if it should be enabled
            // or plural.
            cb.setSelected(false);
            cb.setFocusTraversable(false);
            cb.setOnAction(deleteCheck);

            substitution.setText("{" + (++rowIdx) + "}");
            substitution.setStyle("-fx-alignment: center; -fx-text-alignment: right");
            substitution.setDisable(true);
            substitution.setFocusTraversable(false);
        }
    }

    private final Button addButton = new Button("Add Row");
    private final Button deleteButton = new Button("Delete Row");

    /**
     * Count the number of rows with their checkboxes set to true.
     */
    private long checkBoxCount() {
        return rows.stream().filter(row -> row.cb.isSelected()).count();
    }

    /**
     * Change the Delete Row button depending on what is selected.
     */
    private void processDeleteButton() {
        final var checkboxes = checkBoxCount();
        deleteButton.setDisable(checkboxes == 0L);
        deleteButton.setText("Delete Row" + (checkboxes > 1 ? "(s)" : ""));
    }

    final GridPane gridPane = new GridPane();

    private void populateGridPane() {
        // Remove the old rows and re-add the rows, as deletes can leave blank rows.
        gridPane.getChildren().clear();

        final var substitutionLabel = new Label("Substitution");
        substitutionLabel.setStyle("-fx-text-alignment: center");
        gridPane.add(substitutionLabel, 1, 0);
        final var descriptionLabel = new Label("Description");
        descriptionLabel.setStyle("-fx-text-alignment: center");
        gridPane.add(descriptionLabel, 2, 0);

        // Readd the rows.
        rows.forEach(row -> gridPane.getChildren().addAll(row.cb, row.substitution, row.value));
    }

    public InputPanel() {
        super();
        createUI();
    }

    void createUI() {
        gridPane.setGridLinesVisible(true);
        gridPane.setHgap(10);
        // If we set a Vgap, when we remove rows, since they still technically stay in the GridPane,
        // we end up with uneven spacing.
//        gridPane.setVgap(0);
        gridPane.setPadding(new Insets(10));
        final var c0 = new ColumnConstraints();
        c0.setPercentWidth(5);
        final var c1 = new ColumnConstraints();
        c1.setPercentWidth(15);
        final var c2 = new ColumnConstraints();
        c2.setPercentWidth(80);
        gridPane.getColumnConstraints().addAll(c0, c1, c2);
        populateGridPane();


        final var hbox = new HBox(addButton, deleteButton);
        hbox.setPadding(new Insets(15));
        hbox.setStyle("-fx-alignment: center");
        hbox.setSpacing(100);

        addButton.setOnAction((final ActionEvent e) -> {
            final var row = new Row();
            rows.add(row);
            gridPane.addRow(gridPane.getRowCount(), row.cb, row.substitution, row.value);

            // Modify the UI.
            processDeleteButton();
            VBox.setVgrow(this, Priority.ALWAYS);
        });

        addButton.fire();
        deleteButton.setOnAction((final ActionEvent e) -> {
            // Remove the rows from the UI.
            rows.stream()
                    .filter(row -> row.cb.isSelected())
                    .forEach(row -> gridPane.getChildren().removeAll(row.cb, row.substitution, row.value));

            // Remove the rows from the rows array.
            rows.removeAll(rows.stream().filter(row -> row.cb.isSelected()).collect(Collectors.toList()));

            // Modify the UI.
            populateGridPane();
            processDeleteButton();
            VBox.setVgrow(this, Priority.ALWAYS);
        });

        this.getChildren().addAll(gridPane, hbox);
    }
}
