package com.meti.app;

import com.meti.lib.util.Console;
import com.meti.lib.util.Utility;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/16/2017
 */
public class Main extends Application {
    public static final Console console = new Console("Main");

    private static final File mainFXML;
    private static final File connectFXML;
    private static final File hostFXML;
    private static final File localFXML;

    static {
        File resources = new File("src/resources");

        mainFXML = new File(resources, "Main.fxml");
        connectFXML = new File(resources, "Connect.fxml");
        hostFXML = new File(resources, "Host.fxml");
        localFXML = new File(resources, "Local.fxml");
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Utility.load(mainFXML, primaryStage);
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    public void connect() {
        try {
            Utility.load(connectFXML);
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }

    @FXML
    public void host() {
        try {
            Utility.load(hostFXML);
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }

    @FXML
    public void local() {
        try {
            Utility.load(localFXML);
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }
}
