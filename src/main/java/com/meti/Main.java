package com.meti;

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
    private static final URL displayFXML = Main.class.getResource("Display.fxml");

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLUtil.load(displayFXML).getParent();
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //methods
    public static void main(String[] args) {
        launch(args);
    }
}
