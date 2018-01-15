package com.meti.app;

import com.meti.util.fxml.Controller;
import com.meti.util.fxml.FXUtil;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/15/2018
 */
public class ConnectionController extends Controller {
    private final URL newConnectionFXML = getClass().getResource("NewConnection.fxml");

    @FXML
    private ListView<String> conectionListView;

    @FXML
    public void newConnection() {
        try {
            Parent parent = FXUtil.load(newConnectionFXML).getParent();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }
}
