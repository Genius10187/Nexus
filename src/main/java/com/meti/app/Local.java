package com.meti.app;

import com.meti.lib.io.client.Client;
import com.meti.lib.io.client.ClientState;
import com.meti.lib.io.client.Clients;
import com.meti.lib.io.server.Server;
import com.meti.lib.io.server.Servers;
import com.meti.lib.io.source.ObjectSource;
import com.meti.lib.io.source.Sources;
import com.meti.lib.util.execute.Executables;
import com.meti.lib.util.fx.FXMLBundle;
import com.meti.lib.util.fx.StageableImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

import static com.meti.app.Main.*;
import static com.meti.lib.util.Utility.assertNullParameters;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class Local extends StageableImpl implements Initializable {
    //for the fxml here, we show the absolute path
    //but sometimes we have to use the relative path
    //therefore we have a default path
    @FXML
    private TextField fileField;

    private File defaultLocation = new File("Server");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileField.setText(defaultLocation.getAbsolutePath());
    }

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
            controller.setState(new ClientState(client));
            controller.init();

            console.log(Level.FINE, "Loaded client display");
        } catch (IOException e) {
            console.log(Level.SEVERE, "Failed to load display", e);
        }
    }

    private int loadServer() {
        try {
            Server server = Servers.create(0, Main.console);

            //TODO: alternative

            String path = fileField.getText();
            if (path.equals(defaultLocation.getAbsolutePath())) {
                path = defaultLocation.getPath();
            }
            List<File> loadedFiles = server.getState().getManager().load(new File(path));
            console.log(Level.FINE, "Loaded " + loadedFiles.size() + " files");

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
