package com.meti.app;

import com.meti.util.Action;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/7/2017
 */
class ServerCreator {
    @FXML
    private TextField portField;

    private Action<String> onPortChanged;

    private ServerBuilder serverBuilder;

    @FXML
    public void generateLocalPort() {
        try {
            //temporary socket?
            ServerSocket serverSocket = new ServerSocket(0);

            //I see no reasons why this wouldn't work
            portField.setText(String.valueOf(serverSocket.getLocalPort()));

            serverSocket.close();
        } catch (IOException e) {
            serverBuilder.getConsole().log(e);
        }
    }

    @FXML
    public void openAdvancedSettings() {
        try {
            serverBuilder.openAdvancedDialog();
        } catch (IOException e) {
            serverBuilder.getConsole().log(e);
        }
    }

    @FXML
    public void hostServer() {
        onPortChanged.act(portField.getText());

        try {
            serverBuilder.host();
        } catch (Exception e) {
            //should we give a more detailed report to the user
            //or should we assume the user has an idea
            //on how servers work ¯\_(ツ)_/¯
            serverBuilder.getConsole().log(e);
        }
    }

    public void setOnPortChanged(Action<String> onPortChanged) {
        this.onPortChanged = onPortChanged;
    }

    public void setServerBuilder(ServerBuilder serverBuilder) {
        this.serverBuilder = serverBuilder;
    }
}
