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
import java.util.EnumSet;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/16/2017
 */
public class Utility {
    private Utility() {
    }

    //ugh, why java???
    public static Character[] toObjectArray(char[] chars) {
        Character[] array = new Character[chars.length];
        for (int i = 0; i < chars.length; i++) {
            array[i] = chars[i];
        }
        return array;
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

    public static FXMLBundle load(File file, Stage stage, EnumSet<FXML> flags) throws IOException {
        if (file.exists()) {
            return load(file.toURI().toURL(), stage, flags);
        } else {
            throw new IllegalArgumentException(file.getAbsolutePath() + " does not exist, fxml can't be loaded");
        }
    }

    public static FXMLBundle load(URL url, Stage stage, EnumSet<FXML> flags) throws IOException {
        FXMLLoader loader = new FXMLLoader(url);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        if (flags.contains(FXML.LOAD_STAGE) && stage != null) {
            stage.setScene(scene);
            stage.show();
        }

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

    public static FXMLBundle load(File file, EnumSet<FXML> flags) throws IOException {
        return load(file.toURI().toURL(), flags);
    }

    public static FXMLBundle load(URL url, EnumSet<FXML> flags) throws IOException {
        Stage stage = null;
        if (flags.contains(FXML.LOAD_STAGE)) {
            stage = new Stage();
        }
        return load(url, stage, flags);
    }

    public enum FXML {
        NONE,
        LOAD_STAGE
    }
}
