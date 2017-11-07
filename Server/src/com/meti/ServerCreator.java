package com.meti;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.File;
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
        //why so many try catches?!?!
        try {
            Integer portInteger = Integer.valueOf(portField.getText());

            if(portInteger > 0) {
                this.onPortChanged.act(portInteger);
                this.serverBuilder.host();
            } else {
                Dialog dialog = Utility.openNewDialog();
                dialog.setMessageText("Port entered is not greater or equal to 0");
            }
        } catch (NumberFormatException e) {
            try {
                Dialog dialog = Utility.openNewDialog();
                dialog.setMessageText("Port entered is not a number");
            } catch (IOException e1) {
                //this is a funny exception (an exception caused by an exception, LOL)
                serverBuilder.getConsole().log(e1);
            }
        } catch (IOException e) {
            serverBuilder.getConsole().log(e);
        }
    }

    public void setOnPortChanged(Action<Integer> onPortChanged) {
        this.onPortChanged = onPortChanged;
    }

    public void setServerBuilder(ServerBuilder serverBuilder) {
        this.serverBuilder = serverBuilder;
    }
}
