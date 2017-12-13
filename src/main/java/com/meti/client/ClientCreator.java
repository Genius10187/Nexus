package com.meti.client;

import com.meti.util.Dialog;
import com.meti.util.Utility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;

import static com.meti.client.ClientProperties.logger;

public class ClientCreator {
    private static final URL clientDisplayFile = ClientCreator.class
            .getResource("/fxml/ClientDisplay.fxml");

    @FXML
    private TextField addressField;

    @FXML
    private TextField portField;
    private ExecutorService executorService;

    @FXML
    public void connect() {
        try {
            Socket socket;
            {
                socket = getSocket();
                logger.log(Level.FINE, "Created the socket and connected to the server on port " + socket.getPort());
            }

            //we need to pass both for initializing fields and performing the method
            //because it is an interface
            ClientHandler clientHandler;
            ClientDisplay display;
            Stage stage;
            {
                //TODO: somehow fix this
                clientHandler = new ClientHandler(socket);
                clientHandler.perform(socket);

                //TODO: replace with utility
                FXMLLoader loader = new FXMLLoader(clientDisplayFile);
                Parent parent = loader.load();
                display = loader.getController();

                stage = Utility.buildStage(parent);
                logger.log(Level.FINE, "Loading ClientDisplay fxml");
            }

            {
                display.setSocket(socket);
                display.setHandler(clientHandler);
                display.setStage(stage);

                display.init();
                logger.log(Level.FINE, "Initialized ClientDisplay with sockets");
            }
        } catch (Exception e) {
            try {
                Dialog.loadDialog().setException(e);
            } catch (IOException e1) {
                e1.printStackTrace();

                System.exit(-1);
            }
        }
    }

    private Socket getSocket() throws IOException {
        InetAddress address = InetAddress.getByName(addressField.getText());
        int port = Integer.parseInt(portField.getText());

        return new Socket(address, port);
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}
