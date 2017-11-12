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
    private DisconnectTimer timer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timer = new DisconnectTimer();
        timer.start();
    }

    public void setServer(Server server) {
        this.server = server;

        this.server.setOnClientConnect(this::addClient);
        this.server.setOnClientDisconnect(param -> {
            Request<Client> disconnectRequest = () -> param;

            timer.onRequestFromBuffer(disconnectRequest);
        });

        server.getClients().forEach(this::addClient);
    }

    //feels weird having this method without remove...
    private void addClient(Client param) {
        clientsView.getItems().add(param.getSocket().getInetAddress().toString());
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
