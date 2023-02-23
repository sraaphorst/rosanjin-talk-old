package com.vorpal.rosanjintalk.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javafx.collections.FXCollections.observableArrayList;

public class SplashScreen extends BorderPane {
    private static class RosanjinTalkEntry implements Comparable<RosanjinTalkEntry> {
        public final String name;
        public final String path;

        public RosanjinTalkEntry(final String name, final String path) {
            this.name = name;
            this.path = path;
        }

        @Override
        public int compareTo(final RosanjinTalkEntry other) {
            return name.compareTo(other.name);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private final ListView<RosanjinTalkEntry> fileList = new ListView<>();

    public SplashScreen() {
        super();

        // The file pane
        fileList.setEditable(false);

        try {
            populateFiles();
        } catch (final URISyntaxException | IOException ex) {
            ex.getStackTrace();
        }

        setCenter(fileList);
    }

    public void populateFiles() throws URISyntaxException, IOException {
        final ObservableList<RosanjinTalkEntry> lst = FXCollections.observableArrayList();
        final var path = getPath();
        final var repo = new File(getPath());
        System.out.println(repo);

        // The directory is a file instead of a path?
        if (repo.isFile()) {
            final var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Directory is corrupt");
            alert.setHeaderText(null);
            alert.setContentText("The directory:\n\n" + path + "\n\nthat is supposed to contain FLUKE files\n"
            + "is not a directory!");
            alert.showAndWait();
            System.exit(1);
        }

        if (!repo.exists())
            repo.mkdirs();

        List<RosanjinTalkEntry> result;
        // Now read in the FLUKE files from the directory and populate the lst.
        try (Stream<Path> walk = Files.walk(Paths.get(path), 1)) {
            result = walk
                    .filter(p -> !Files.isDirectory(p))
                    .map(p -> p.toString().toLowerCase())
                    .filter(f -> f.endsWith(".fluke"))
                    .map(f -> {
                        final var filenameIdx = f.lastIndexOf(File.separator);
                        final var filename = f.substring(filenameIdx + 1);
                        return new RosanjinTalkEntry(filename, f);
                    })
                    .sorted()
                    .collect(Collectors.toUnmodifiableList());
        }

        lst.setAll(result);
        fileList.setItems(lst);
    }

    private String getPath() throws URISyntaxException {
        final var location = getClass()
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI();
        return new File(location).getParentFile().getPath();
    }
}
