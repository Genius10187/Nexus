package com.meti;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/7/2017
 */
public class ServerMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        ServerBuilder builder = new ServerBuilder();
        try {
            builder.openDefaultDialog(primaryStage);
        } catch (IOException e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
