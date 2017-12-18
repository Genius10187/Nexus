package com.meti.app;

import com.meti.lib.util.Utility;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/16/2017
 */
public class Main extends Application {
    private static final URL fxmlURL = Main.class.getResource("/com/meti/Main.fxml");

    @Override
    public void start(Stage primaryStage) throws Exception {
        Utility.load(fxmlURL, primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    public void connectServer() {

    }

    @FXML
    public void hostServer() {

    }

    @FXML
    public void localServer() {

    }
}
