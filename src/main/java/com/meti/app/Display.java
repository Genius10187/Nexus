package com.meti.app;

import com.meti.util.FXMLUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/14/2018
 */
public class Display {
    private final URL chatFXML = getClass().getResource("Chat.fxml");

    @FXML
    private BorderPane contentPane;

    @FXML
    public void openChat() {
        try {
            Parent parent = FXMLUtil.load(chatFXML).getParent();
            contentPane.setCenter(parent);

            //TODO: multiple places instead of BorderPane.setCenter(Node node)
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: replace caught exception with console
        }
    }

    @FXML
    public void close() {
        Platform.exit();
    }
}