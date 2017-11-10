package com.meti;

import com.meti.util.Console;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientMain extends Application {
    private final Console console = new Console();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClientBuilder builder = new ClientBuilder(console);
        builder.openDialog(primaryStage);
    }
}
