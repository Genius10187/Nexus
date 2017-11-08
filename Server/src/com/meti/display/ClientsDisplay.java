package com.meti.display;

import com.meti.Server;
import com.meti.io.Client;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ClientsDisplay {
    private Server server;

    @FXML
    private ListView<String> clientsView;

    public void setServer(Server server) {
        this.server = server;

        this.server.setOnClientConnect(this::addClient);
        this.server.setOnClientDisconnect(param -> {
            String addressString = param.getSocket().getInetAddress().toString();
            if (!clientsView.getItems().remove(addressString)) {
                //should probably not happen
                server.getConsole().log("Client " + param.getSocket().getInetAddress() + " has not been registered in the clients display");
            }
        });

        server.getClients().forEach(this::addClient);
    }

    //feels weird having this method without remove...
    private void addClient(Client param) {
        clientsView.getItems().add(param.getSocket().getInetAddress().toString());
    }
}
