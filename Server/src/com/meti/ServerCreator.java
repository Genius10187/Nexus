package com.meti;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/7/2017
 */
public class ServerCreator {
    @FXML
    private TextField portField;

    private Action<Integer> onPortChanged;

    private ServerBuilder serverBuilder;

    @FXML
    public void generateLocalPort() {
        try {
            Server server = serverBuilder.buildServer();

            //I see no reasons why this wouldn't work
            portField.setText(String.valueOf(server.getServerSocket().getLocalPort()));
        } catch (IOException e) {
            //TODO: handle exception, should really be next task
            e.printStackTrace();
        }
    }

    @FXML
    public void openAdvancedSettings() {
        serverBuilder.openAdvancedDialog();
    }

    @FXML
    public void hostServer() {
        //TODO: NumberFormatException handle
        this.onPortChanged.act(Integer.valueOf(portField.getText()));
        this.serverBuilder.host();
    }

    public void setOnPortChanged(Action<Integer> onPortChanged) {
        this.onPortChanged = onPortChanged;
    }

    public void setServerBuilder(ServerBuilder serverBuilder) {
        this.serverBuilder = serverBuilder;
    }
}
