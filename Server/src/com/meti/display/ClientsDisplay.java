package com.meti.display;

import com.meti.Server;
import com.meti.io.Client;
import com.meti.util.BufferedTimer;
import com.meti.util.Request;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientsDisplay implements Initializable {
    private Server server;

    @FXML
    private ListView<String> clientsView;

    private ConnectTimer connectTimer;
    private DisconnectTimer disconnectTimer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectTimer = new ConnectTimer();
        connectTimer.start();

        disconnectTimer = new DisconnectTimer();
        disconnectTimer.start();
    }

    public void setServer(Server server) {
        this.server = server;

        //how repetitive?
        this.server.setOnClientConnect(param -> {
            Request<Client> connectRequest = () -> param;

            connectTimer.onRequest(connectRequest);
        });

        this.server.setOnClientDisconnect(param -> {
            Request<Client> disconnectRequest = () -> param;

            disconnectTimer.onRequest(disconnectRequest);
        });
    }

    //do we really need the request class?
    private class ConnectTimer extends BufferedTimer<Client> {
        @Override
        public void onRequestFromBuffer(Request<Client> request) {
            clientsView.getItems().add(request.request().getSocket().getInetAddress().toString());
        }
    }

    private class DisconnectTimer extends BufferedTimer<Client> {
        @Override
        public void onRequestFromBuffer(Request<Client> request) {
            InetAddress address = request.request().getSocket().getInetAddress();
            String addressString = address.toString();
            if (!clientsView.getItems().remove(addressString)) {
                //should probably not happen
                server.getConsole().log("Client " + address + " has not been registered in the clients display");
            }
        }
    }
}
