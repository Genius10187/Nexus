package com.meti.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/7/2017
 */
public class Utility {
    private Utility() {
    }

    public static <T> T castIfOfInstance(Object obj, Class<T> c) {
        if (c.isAssignableFrom(obj.getClass())) {
            return c.cast(obj);
        } else {
            return null;
        }
    }

    //untestable
    public static List<File> search(File directory, String... extensions) {
        ArrayList<File> files = new ArrayList<>();
        search(directory, files, extensions);
        return files;
    }

    //untestable
    private static void search(File file, ArrayList<File> files, String... extensions) {
        if (file.isDirectory()) {
            File[] fileArray = file.listFiles();
            if (fileArray != null && fileArray.length != 0) {
                for (File foundFile : fileArray) {
                    search(foundFile, files, extensions);
                }
            }
        } else {
            if (extensions.length == 0) {
                files.add(file);
            } else {
                for (String ext : extensions) {
                    if (getExtension(file).equals(ext)) {
                        files.add(file);
                    }
                }
            }
        }
    }

    public static boolean ensureExists(File file) throws IOException {
        if (file.isDirectory()) {
            return file.mkdirs();
        } else {
            File parent = file.getParentFile();
            if (parent != null) {
                ensureExists(parent);
            }

            return file.createNewFile();
        }
    }

    public static String getExtension(File file) {
        String[] nameArgs = file.getName().split("\\.");
        return nameArgs[nameArgs.length - 1];
    }

    //untestable
    public static Dialog openNewDialog() throws IOException {
        return buildFXML(new File("Core\\assets\\fxml\\Dialog.fxml"), null);
    }

    //untestable
    public static <T> T buildFXML(File location, Stage preexisting) throws IOException {
        return buildFXML(new FileInputStream(location), preexisting);
    }

    //untestable
    public static <T> T buildFXML(InputStream inputStream, Stage preexisting) throws IOException {
        Stage toSet = (preexisting != null) ? preexisting : new Stage();

        FXMLLoader loader = new FXMLLoader();
        Parent parent = loader.load(inputStream);
        toSet.setScene(new Scene(parent));

        if (!toSet.isShowing()) {
            toSet.show();
        }

        return loader.getController();
    }
}
