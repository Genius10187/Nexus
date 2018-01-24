package com.meti.app;

import com.meti.util.fxml.Controller;
import com.meti.util.fxml.FXUtil;
import javafx.fxml.FXML;
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
    private final URL addConnectionFXML = getClass().getResource("AddConnection.fxml");
    private final URL addListenerFXML = getClass().getResource("AddListener.fxml");

    @FXML
    private ListView<String> conectionListView;

    @FXML
    public void addConnection() {
        try {
            FXUtil.load(addConnectionFXML, new Stage());
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }

    @FXML
    public void addListener() {
        try {
            FXUtil.load(addListenerFXML, new Stage());
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }
}