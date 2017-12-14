package com.meti.util.misc;

import com.meti.util.fx.FXBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Utility {

    private Utility() {
    }

    /**
     * Builds a FXBundle from a url.
     * Creates an FXMLLoader and loads the components.
     * Builds the stage at {@link #buildStage(Parent)} , and displays it.
     * Assembles an FXBundle from the components and returns it.
     *
     * @param url The url.
     * @return The FXBundle.
     * @throws IOException If an I/O error occurred.
     */
    public static FXBundle buildStage(URL url) throws IOException {
        FXMLLoader loader = new FXMLLoader(url);
        Parent parent = loader.load();
        Object obj = loader.getController();
        Stage stage = buildStage(parent);

        return new FXBundle(parent, obj, stage);
    }

    /**
     * Builds a stage from a parent.
     * Creates a new scene, which is assigned by a stage.
     * Shows the stage and returns it.
     *
     * @param parent
     * @return
     */
    public static Stage buildStage(Parent parent) {
        Scene scene = new Scene(parent);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        return stage;
    }

    public static List<File> search(File directory, String... extensions) {
        List<File> fileList = new ArrayList<>();
        searchList(directory, fileList, extensions);
        return fileList;
    }

    private static void searchList(File file, List<File> list, String... extensions) {
        if (!list.contains(file)) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null && files.length != 0) {
                    for (File f : files) {
                        searchList(f, list);
                    }
                }
            } else {
                if (extensions.length != 0) {
                    for (String ext : extensions) {
                        if (getExtension(file).equals(ext)) {
                            list.add(file);
                        }
                    }
                } else {
                    list.add(file);
                }
            }
        }
    }

    public static String getExtension(File file) {
        String[] fileNameArgs = file.getName().split("\\.");
        return fileNameArgs[fileNameArgs.length - 1];
    }
}
