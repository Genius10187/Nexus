package com.meti.display;

import com.meti.Server;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class FilesDisplay implements Initializable {
    @FXML
    private TreeView<String> filesView;
    private Server server;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO: AssetManager
    }

    public void setServer(Server server) {
        this.server = server;

        //TODO: build files from server / assetManager
    }
}
