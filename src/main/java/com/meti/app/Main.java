package com.meti.app;

import com.meti.lib.util.Console;
import com.meti.lib.util.Utility;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2017
 */
public class Main extends Application {
    public static final Console console = new Console("Login");
    public static final File resources = new File("src/resources");

    public static final ExecutorService service = Executors.newCachedThreadPool();

    private static final File mainFXML = new File(resources, "Login.fxml");
    private static final long SHUTDOWN_WAIT_TIME = 5000;

    private static Main instance;

    @Override
    public void start(Stage primaryStage) {
        instance = this;

        console.log(Level.INFO, "Starting application");

        try {
            Utility.load(mainFXML, primaryStage, EnumSet.of(Utility.FXML.LOAD_STAGE));
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }

    @Override
    public void stop() {
        console.log(Level.INFO, "Stopping application");

        console.log(Level.INFO, "Shutting down service.");
        service.shutdown();

        if (!service.isShutdown()) {
            console.log(Level.INFO, "Service not shut down, forcing shut down!");

            try {
                Thread.sleep(SHUTDOWN_WAIT_TIME);
                List<Runnable> runnables = service.shutdownNow();
                console.log(Level.INFO, "Shut down server with " +
                        runnables.size() +
                        " runnables left");
            } catch (InterruptedException e) {
                console.log(Level.SEVERE, "Stop thread interrupted!", e);
            }
        }

        System.exit(0);
    }

    public static Main getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
