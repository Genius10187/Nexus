package com.meti.lib.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/16/2017
 */
public class Utility {
    private Utility() {
    }

    public static void load(URL url, Stage stage) throws IOException {
        assertNullParameters(null, stage);

        Parent parent = FXMLLoader.load(url);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    private static void assertNullParameters(Object... parameters) {
        for (int i = 0; i < parameters.length; i++) {
            Object obj = parameters[i];
            if (obj == null) {
                throw new NullPointerException("Object at index " + i + " cannot be null");
            }
        }
    }
}
