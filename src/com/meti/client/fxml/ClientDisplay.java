package com.meti.client.fxml;

import com.meti.client.Client;
import com.meti.server.util.Command;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import static com.meti.Main.getInstance;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/22/2017
 */
public class ClientDisplay implements Initializable {
    @FXML
    public ListView fileListView;

    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    public void openEditors() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Command command = new Command("list", "files");
            client.send(command, true);
        } catch (IOException e) {
            getInstance().log(Level.WARNING, e);
        }
    }
}
