package com.meti.lib.util;

import com.meti.lib.util.fx.FXMLBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/16/2017
 */
public class Utility {
    private Utility() {
    }

    public static List<File> scan(File directory, String... extensions) {
        List<File> fileList = new ArrayList<>();
        scan(directory, fileList, extensions);
        return fileList;
    }

    public static void scan(File file, List<File> files, String[] extensions) {
        if (file.isDirectory()) {
            files.add(file);

            File[] directoryFiles = file.listFiles();

            if (directoryFiles != null && directoryFiles.length != 0) {
                for (File f : directoryFiles) {
                    scan(f, files, extensions);
                }
            }
        } else {
            if (extensions.length != 0) {
                String fileExtension = getExtension(file);

                for (String ext : extensions) {
                    if (fileExtension.equals(ext)) {
                        files.add(file);
                        return;
                    }
                }
            } else {
                files.add(file);
            }
        }
    }

    public static String getExtension(File file) {
        String[] splitName = file.getName().split("\\.");
        return splitName[splitName.length - 1];
    }

    public static FXMLBundle load(File file, Stage stage) throws IOException {
        if (file.exists()) {
            return load(file.toURI().toURL(), stage);
        } else {
            throw new IllegalArgumentException(file.getAbsolutePath() + " does not exist, fxml can't be loaded");
        }
    }

    public static FXMLBundle load(URL url, Stage stage) throws IOException {
        assertNullParameters(url, stage);

        FXMLLoader loader = new FXMLLoader(url);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();

        return new FXMLBundle(loader, parent, loader.getController(), stage);
    }

    public static void assertNullParameters(Object... parameters) {
        for (int i = 0; i < parameters.length; i++) {
            Object obj = parameters[i];
            if (obj == null) {
                throw new NullPointerException("Object at index " + i + " cannot be null");
            }
        }
    }

    public static FXMLBundle load(File file) throws IOException {
        return load(file.toURI().toURL());
    }

    public static FXMLBundle load(URL url) throws IOException {
        Stage stage = new Stage();
        return load(url, stage);
    }
}
