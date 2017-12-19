package com.meti.app;

import com.meti.lib.io.client.Client;
import com.meti.lib.io.client.Clients;
import com.meti.lib.io.server.Server;
import com.meti.lib.io.server.Servers;
import com.meti.lib.io.source.ObjectSource;
import com.meti.lib.io.source.Sources;
import com.meti.lib.util.execute.Executables;
import com.meti.lib.util.fx.FXMLBundle;
import com.meti.lib.util.fx.StageableImpl;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;

import static com.meti.app.Main.*;
import static com.meti.lib.util.Utility.assertNullParameters;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class Local extends StageableImpl {
    @FXML
    private TextField fileField;

    @FXML
    public void openViewer() {
        DirectoryChooser chooser = new DirectoryChooser();
        File returned = chooser.showDialog(stage);
        fileField.setText(returned.getPath());
    }

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
        int port = loadServer();
        Client client = loadClient(port);

        loadDisplay(client);
    }

    private void loadDisplay(Client client) {
        assertNullParameters(client);

        try {
            FXMLBundle bundle = load(new File(resources, "ClientDisplay.fxml"));
            ClientDisplay controller = (ClientDisplay) bundle.getController();
            controller.setClient(client);
            controller.init();

            console.log(Level.FINE, "Loaded client display");
        } catch (IOException e) {
            console.log(Level.SEVERE, "Failed to load display", e);
        }
    }

    private int loadServer() {
        try {
            Server server = Servers.create(0, Main.console);
            int localPort = server.getPort();

            Executables.execute(service, server);

            console.log(Level.FINE, "Loaded server");

            return localPort;
        } catch (IOException e) {
            console.log(Level.SEVERE, "Failed to load server", e);
        }

        return -1;
    }

    private Client loadClient(int port) {
        try {
            Socket socket = new Socket(InetAddress.getByName("localhost"), port);
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
