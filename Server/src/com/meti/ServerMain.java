package com.meti;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/5/2017
 */
public class ServerMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        File serverHoster = new File("assets\\ServerHoster.fxml");
        FXMLLoader loader = new FXMLLoader(serverHoster.toURI().toURL());

        Parent parent = loader.load();
        ServerHoster controller = loader.getController();

        primaryStage.setScene(new Scene(parent));
        primaryStage.show();

        Server server = controller.getServer();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
