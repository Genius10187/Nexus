package com.meti.client;

import com.meti.util.Console;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class ClientMain extends Application {
    private final Console console = new Console();
    private ClientBuilder builder;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        builder = new ClientBuilder(console);
        builder.openCreatorDialog(primaryStage);
    }

    @Override
    public void stop() {
        try {
            Socket socket = builder.getClient().getSocket();
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.exit(-1);
        }
    }
}
