package com.meti.client;

import com.meti.util.Dialog;
import com.meti.util.LoggerHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ClientCreator {
    private static final File clientDisplayFile = new File("assets\\fxml\\ClientDisplay.fxml");

    //??????
    private static final Logger logger = Logger.getLogger("Application");

    @FXML
    private TextField addressField;

    @FXML
    private TextField portField;
    private ExecutorService executorService;


    @FXML
    public void connect() {
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);

        Handler handler = new LoggerHandler();
        handler.setFormatter(new SimpleFormatter());
        handler.setLevel(Level.ALL);

        logger.addHandler(handler);

        //TODO: logger writing too many times
        logger.log(Level.INFO, "Initializing application");

        try {
            InetAddress address = InetAddress.getByName(addressField.getText());
            int port = Integer.parseInt(portField.getText());

            Socket socket = new Socket(address, port);

            //we need to pass both for initializing fields and performing the method
            //because it is an interface
            ClientHandler clientHandler = new ClientHandler(logger, executorService, socket);
            clientHandler.perform(socket);

            //TODO: client display
            FXMLLoader loader = new FXMLLoader(clientDisplayFile.toURI().toURL());
            Parent parent = loader.load();
            ClientDisplay display = loader.getController();

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            //consider creating client class
            display.setSocket(socket);
            display.setHandler(clientHandler);

            display.init();
        } catch (Exception e) {
            try {
                Dialog.loadDialog().setException(e);
            } catch (IOException e1) {
                e1.printStackTrace();

                System.exit(-1);
            }
        }
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}
