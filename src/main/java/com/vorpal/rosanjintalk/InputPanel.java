package com.vorpal.rosanjintalk;

// By Sebastian Raaphorst, 2023.

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An input panel to manage the substitution information for the main corpus.
 */
final public class InputPanel extends BorderPane {
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

        public Row() {
            // We check the delete button when the checkboxes are checked to see if it should be enabled
            // or plural.
            cb.setSelected(false);
            cb.setFocusTraversable(false);
            cb.setOnAction(deleteCheck);
            cb.setAlignment(Pos.CENTER_RIGHT);

            substitution.setText("{" + (++rowIdx) + "}");
            substitution.setAlignment(Pos.CENTER);;
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
        deleteButton.setText("Delete Row" + (checkboxes > 1 ? "s" : ""));
    }

    public InputPanel() {
        super();
        createUI();
    }

    void createUI() {
        final GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10));

        // Constraints on the columns.
        final var c0 = new ColumnConstraints();
        c0.setPercentWidth(10);
        final var c1 = new ColumnConstraints();
        c1.setPercentWidth(20);
        final var c2 = new ColumnConstraints();
        c2.setPercentWidth(70);
        gridPane.getColumnConstraints().addAll(c0, c1, c2);

        final var substitutionLabel = new Label("Substitute");
        substitutionLabel.setTextAlignment(TextAlignment.CENTER);
        gridPane.add(substitutionLabel, 1, 0);
        final var descriptionLabel = new Label("Description");
        descriptionLabel.setTextAlignment(TextAlignment.CENTER);
        gridPane.add(descriptionLabel, 2, 0);

        // Add the rows.
        rows.forEach(row -> {
//            gridPane.getChildren().addAll(row.cb, row.substitution, row.value)
            final var rows = gridPane.getRowCount();
            gridPane.add(row.cb, 0, rows);
            gridPane.add(row.substitution, 1, rows);
            gridPane.add(row.value, 2, rows);
        });

        // Wrap the GridPane in a vertical ScrollPane.
        final var scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setContent(gridPane);
//        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        // Do not allow horizontal scrolling.
        scrollPane.addEventFilter(ScrollEvent.SCROLL,
                evt -> { if (evt.getDeltaX() != 0) evt.consume(); });

        // Create the button panel.
        final var buttonBox = new HBox(addButton, deleteButton);
        buttonBox.setPadding(new Insets(15));
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(100);

        addButton.setOnAction((final ActionEvent e) -> {
            final var row = new Row();
            rows.add(row);
            gridPane.addRow(gridPane.getRowCount(), row.cb, row.substitution, row.value);

            // Modify the UI.
            processDeleteButton();
        });

        deleteButton.setOnAction((final ActionEvent e) -> {
            // Remove the rows from the UI.
            rows.stream()
                    .filter(row -> row.cb.isSelected())
                    .forEach(row -> gridPane.getChildren().removeAll(row.cb, row.substitution, row.value));

            // Remove the rows from the rows array.
            rows.removeAll(rows.stream().filter(row -> row.cb.isSelected()).collect(Collectors.toList()));

            // Modify the UI.
            processDeleteButton();
        });

        // Put everything together in this BorderPane.
        setPadding(new Insets(20));
        setCenter(scrollPane);
        setBottom(buttonBox);
    }
}
