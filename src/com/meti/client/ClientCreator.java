package com.meti.client;

import com.meti.util.Dialog;
import com.meti.util.LoggerHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ClientCreator {
    private static final File clientDisplayFile = new File("asset\\fxml\\ClientDisplay.fxml");
    private static final Logger logger = Logger.getLogger("Application");

    @FXML
    private TextField addressField;

    @FXML
    private TextField portField;


    @FXML
    public void connect() {
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);

        Handler handler = new LoggerHandler();
        handler.setFormatter(new SimpleFormatter());
        handler.setLevel(Level.ALL);

        logger.addHandler(handler);
        logger.log(Level.INFO, "Initializing application");

        try {
            InetAddress address = InetAddress.getByName(addressField.getText());
            int port = Integer.parseInt(portField.getText());

            Socket socket = new Socket(address, port);
            ClientHandler clientHandler = new ClientHandler();
            clientHandler.perform(socket);

        } catch (Exception e) {
            try {
                Dialog.loadDialog().setException(e);
            } catch (IOException e1) {
                e1.printStackTrace();

                System.exit(-1);
            }
        }
    }
}
