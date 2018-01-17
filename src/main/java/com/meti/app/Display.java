package com.meti.app;

import com.meti.util.fxml.Controller;
import com.meti.util.fxml.FXUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/14/2018
 */
public class Display extends Controller {
    private final URL connectionControllerFXML = getClass().getResource("ConnectionController.fxml");
    private final URL newConnectionFXML = getClass().getResource("NewConnection.fxml");
    private final URL chatFXML = getClass().getResource("Chat.fxml");

    @FXML
    private BorderPane contentPane;

    @FXML
    public void openChat() {
        try {
            Parent parent = FXUtil.load(chatFXML).getParent();
            contentPane.setCenter(parent);

            //TODO: multiple places instead of BorderPane.setCenter(Node node)
        } catch (IOException e) {
            console.log(Level.SEVERE, "Failed to load view", e);
        }
    }

    @FXML
    public void openConnections() {
        try {
            FXUtil.load(connectionControllerFXML, null);
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }

    @FXML
    public void openNewConnection() {
        try {
            FXUtil.load(newConnectionFXML, null);
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }

    @FXML
    public void close() {
        Platform.exit();
    }
}