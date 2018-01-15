package com.meti.app;

import com.meti.util.fxml.FXUtil;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/14/2018
 */
public class Main extends Application {
    //fields
    public static final AppState appState = new AppState();

    private final URL displayFXML = getClass().getResource("Display.fxml");

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXUtil.load(displayFXML).getParent();
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
    }

    //methods
    public static void main(String[] args) {
        launch(args);
    }
}
