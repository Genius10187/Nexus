package com.meti.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.net.Socket;

public class ClientDisplay {
    @FXML
    private TabPane editorTabPane;

    @FXML
    private ListView<String> fileListView;

    private Socket socket;
    private ClientHandler handler;

    @FXML
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Platform.exit();

        //concerning exit statement
        System.exit(0);
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setHandler(ClientHandler handler) {
        this.handler = handler;
    }
}
