package com.meti.app;

import com.meti.util.fxml.FXUtil;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/14/2018
 */
public class Main extends Application {
    //fields
    public static final AppState appState = new AppState();

    private final URL displayFXML = getClass().getResource("Display.fxml");

    //methods
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXUtil.load(displayFXML).getParent();
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        try {
            ExecutorService service = appState.getService();
            service.shutdown();

            if (!service.isShutdown()) {
                Thread.sleep(1000);

                List<Runnable> runnables = service.shutdownNow();
                if (runnables.size() > 0) {
                    appState.getConsole().log(Level.WARNING, "Executor service shut down with " + runnables.size() + " runnables remaining");
                }
            } else {
                appState.getConsole().log(Level.INFO, "Shut down executor service successfully.");
            }
        } catch (InterruptedException e) {
            appState.getConsole().log(Level.SEVERE, e);
        }
    }
}
