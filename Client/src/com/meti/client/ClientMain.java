package com.meti.client;

import com.meti.io.Client;
import com.meti.util.Console;
import javafx.application.Application;
import javafx.stage.Stage;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientMain extends Application {
    private static ClientMain instance;

    private final Console console = new Console();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private ClientBuilder builder;

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;

        builder = new ClientBuilder(console);
        builder.openCreatorDialog(primaryStage);
    }

    @Override
    public void stop() {
        try {
            Client client = builder.getClient();
            if (client != null) {
                Socket socket = client.getSocket();
                if (socket != null) {
                    socket.close();
                }
            }

            executorService.shutdown();

            Thread.sleep(5000);

            if (!executorService.isShutdown()) {
                executorService.shutdownNow();
            }
        } catch (Exception e) {
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static ClientMain getInstance() {
        return instance;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
