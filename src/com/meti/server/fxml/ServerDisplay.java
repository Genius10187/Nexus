package com.meti.server.fxml;

import com.meti.server.Server;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.Socket;

import static com.meti.Main.getInstance;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/20/2017
 */
public class ServerDisplay {
    private Server server;

    @FXML
    private ListView<String> socketListView;

    public void setServer(Server server) {
        this.server = server;

        server.setOnClientConnect(params -> {
            for (Socket socket : params) {
                socketListView.getItems().add(socket.getInetAddress().toString());
            }
        });

        server.setOnClientDisconnect(params -> {
            for (Socket socket : params) {
                socketListView.getItems().remove(socket.getInetAddress().toString());
            }
        });
    }

    @FXML
    public void openConsole() {
        Object controller = getInstance().buildStage("assets\\fxml\\ServerConsole.fxml", null);
        if (controller instanceof ServerConsole) {
            ((ServerConsole) controller).setServer(server);
        } else {
            throw new RuntimeException("Controller not instance of ServerConsole");
        }
    }
}
