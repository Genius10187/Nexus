package com.meti.server;

import com.meti.util.LoggerHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/30/2017
 */
public class ServerMain extends Application {
    private static final Logger logger = Logger.getLogger("Application");
    private static File serverCreatorFXMLLocation = new File("/fxml/ServerCreator.fxml");

    static {
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);

        Handler handler = new LoggerHandler();
        handler.setFormatter(new SimpleFormatter());
        handler.setLevel(Level.ALL);

        logger.addHandler(handler);
    }

    public static void log(Level level, Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        log(level, writer.toString());
    }

    public static void log(Level level, String message) {
        logger.log(level, message);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(serverCreatorFXMLLocation.toURI().toURL());
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
