package com.meti;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/7/2017
 */
public class ServerMain extends Application {
    private final Console console = new Console();

    @Override
    public void start(Stage primaryStage) {
        console.log("Starting application.");

        try {
            //should we really put console as a field here? might cause bugs...
            ServerBuilder builder = new ServerBuilder(console);

            builder.openDefaultDialog(primaryStage);
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
