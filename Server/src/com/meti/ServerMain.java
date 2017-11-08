package com.meti;

import com.meti.app.ServerBuilder;
import com.meti.util.Console;
import javafx.application.Application;
import javafx.application.Platform;
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

    private ServerBuilder builder;

    @Override
    public void start(Stage primaryStage) {
        console.log("Starting application.");

        try {
            //should we really put console as a field here? might cause bugs...
            builder = new ServerBuilder(console);
            builder.openDefaultDialog(primaryStage);
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }

    @Override
    public void stop() {
        console.log("Stopping application");

        try {
        	//random change, bug
        	if(builder != null && builder.getServer() != null) {
        		builder.getServer().stop();
        	}
            Platform.exit();
        } catch (IOException | InterruptedException e) {
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
