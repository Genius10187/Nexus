package com.meti.util.fxml;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/14/2018
 */
public class FXMLUtil {
    private FXMLUtil() {
    }

    //methods
    public static <T> FXMLBundle<T> load(URL url) throws IOException {
        FXMLLoader loader = new FXMLLoader(url);
        Parent parent = loader.load();
        T controller = loader.getController();
        return new FXMLBundle<>(url, parent, controller);
    }
}