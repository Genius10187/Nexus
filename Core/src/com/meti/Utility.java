package com.meti;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/7/2017
 */
public class Utility {
    private Utility() {
    }

    public static Dialog openNewDialog() throws IOException {
        return buildFXML(new File("Core\\assets\\fxml\\Dialog.fxml"), null);
    }

    //untestable
    public static <T> T buildFXML(File location, Stage preexisting) throws IOException {
        return buildFXML(new FileInputStream(location), preexisting);
    }

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
