package com.meti.server;

import com.meti.util.Dialog;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/3/2017
 */
public class ServerCreator {
    @FXML
    private TextField portField;

    private ServerSocket serverSocket;

    @FXML
    public void host() {
        try {
            if (serverSocket == null) {
                serverSocket = new ServerSocket(Integer.parseInt(portField.getText()));
            }

            Server server = new Server(serverSocket);
            server.init();
            server.run();
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
}
