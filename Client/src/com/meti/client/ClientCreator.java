package com.meti.client;

import com.meti.io.Client;
import com.meti.util.Dialog;
import com.meti.util.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

class ClientCreator {
    @FXML
    private TextField addressField;

    @FXML
    private TextField portField;
    private ClientBuilder clientBuilder;

    @FXML
    public void connect() {
        try {
            InetAddress address = InetAddress.getByName(addressField.getText());
            int port = Integer.parseInt(portField.getText());
            Socket socket = new Socket(address, port);
            Client client = new Client(socket);
            clientBuilder.setClient(client);
            clientBuilder.openDisplayDialog();
        } catch (Exception e) {
            try {
                Dialog dialog = Utility.buildFXML(new File("Core\\assets\\fxml\\Dialog.fxml"), null);
                dialog.setMessageTextAndException("Failed to connect to server", e);
            } catch (IOException e1) {
                clientBuilder.getConsole().log(e1);
            }
        }
    }

    public void setClientBuilder(ClientBuilder clientBuilder) {
        this.clientBuilder = clientBuilder;
    }
}