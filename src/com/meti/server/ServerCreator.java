package com.meti.server;

import com.meti.util.Dialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/3/2017
 */
public class ServerCreator {
    private static final File serverDisplayFXMLLocation = new File("assets\\fxml\\ServerDisplay.fxml");

    @FXML
    private TextField portField;

    private ServerSocket serverSocket;

    @FXML
    public void host() {
        try {
            if (serverSocket == null) {
                serverSocket = new ServerSocket(Integer.parseInt(portField.getText()));
            }

            FXMLLoader loader = new FXMLLoader(serverDisplayFXMLLocation.toURI().toURL());
            Parent parent = loader.load();
            ServerDisplay display = loader.getController();

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            Server server = new Server(serverSocket);

            display.setServer(server);
            display.buildListeners();

            ServerThread serverThread = new ServerThread(server);
            server.getExecutorService().submit(serverThread);
        } catch (NumberFormatException e) {
            try {
                Dialog.loadDialog().setMessage("Invalid port!");
            } catch (IOException e1) {
                ServerMain.log(Level.SEVERE, e1);
            }
        } catch (IOException e) {
            try {
                Dialog.loadDialog().setException(e);
            } catch (IOException e1) {
                ServerMain.log(Level.SEVERE, e1);
            }
        }
    }

    @FXML
    public void generateLocalPort() {
        try {
            this.serverSocket = new ServerSocket(0);

            portField.setText(String.valueOf(serverSocket.getLocalPort()));
        } catch (IOException e) {
            try {
                Dialog.loadDialog().setException(e);
            } catch (IOException e1) {
                ServerMain.log(Level.SEVERE, e1);
            }
        }
    }

    @FXML
    public void openAdvancedSettings() {
        //TODO: advanced settings
    }

    private class ServerThread implements Runnable {
        private final Server server;

        public ServerThread(Server server) {
            this.server = server;
        }

        @Override
        public void run() {
            server.init();
            server.run();
        }
    }
}
