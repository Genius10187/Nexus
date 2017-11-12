package com.meti.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
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

    public static Class<?> getClassFromFile(File directory, File file) throws FileNotFoundException, ClassNotFoundException {
        String extension = getExtension(file);
        if (extension.equals("java") || extension.equals("class")) {
            if (search(directory, "class", "java").contains(file)) {
                String classPath = file.getPath().replace(directory.getPath(), "");
                classPath = classPath.substring(1, classPath.length());
                classPath = classPath.replaceAll("\\\\", ".");

                //5 for .java
                classPath = classPath.substring(0, classPath.length() - 5);

                return Class.forName(classPath);
            } else {
                throw new FileNotFoundException("Directory " + directory.getPath() + " does not contain" +
                        "file " + file.getPath());
            }
        } else {
            throw new IllegalArgumentException("File does not end with class or java");
        }
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
