package com.meti.app;

import com.meti.lib.io.client.Client;
import com.meti.lib.io.client.ClientState;
import com.meti.lib.io.client.Clients;
import com.meti.lib.io.source.ObjectSource;
import com.meti.lib.io.source.Sources;
import com.meti.lib.util.execute.Executables;
import com.meti.lib.util.fx.FXMLBundle;
import com.meti.lib.util.fx.StageableImpl;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;

import static com.meti.app.Main.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class Connect extends StageableImpl {
    @FXML
    private TextField addressField;

    @FXML
    private TextField portField;

    @FXML
    public void back() {
        try {
            load(new File(resources, "Login.fxml"));
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }

    @FXML
    public void next() {
        Client client = loadClient();
        loadDisplay(client);
    }

    private void loadDisplay(Client client) {
        try {
            FXMLBundle bundle = load(new File(resources, "ClientDisplay.fxml"));
            ClientDisplay controller = (ClientDisplay) bundle.getController();
            controller.setState(new ClientState(client));
            controller.run();

            console.log(Level.FINE, "Loaded client display");
        } catch (IOException e) {
            console.log(Level.SEVERE, "Failed to load display", e);
        }
    }

    private Client loadClient() {
        try {
            InetAddress address = InetAddress.getByName(addressField.getText());
            int port = Integer.parseInt(portField.getText());

            Socket socket = new Socket(address, port);
            ObjectSource source = Sources.createObjectSource(socket);
            Client client = Clients.create(source);

            Executables.execute(service, client);

            console.log(Level.FINE, "Loaded client");

            return client;
        } catch (IOException e) {
            console.log(Level.SEVERE, "Failed to load client", e);
        }

        return null;
    }
}
