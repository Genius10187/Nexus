package com.meti;

import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/7/2017
 */
public class ServerBuilder {
    private Server server;

    //TODO: advanced settings will update these fields...
    private InetAddress localAddress;
    private int maxQueueSize;
    private int port;

    //default field values
    public ServerBuilder() {
        localAddress = null;
        maxQueueSize = 50;
        port = 0;
    }

    public Server buildServer() throws IOException {
        if (localAddress != null) {
            server = new Server(port, maxQueueSize, localAddress);
        } else {
            server = new Server(port, maxQueueSize);
        }

        // do we really need "server" as a field?
        return server;
    }

    //we have primaryStage here because this is the first fxml loaded by the main class
    public void openDefaultDialog(Stage primaryStage) throws IOException {
        ServerCreator creator = Utility.buildFXML(
                new File("Server\\assets\\fxml\\ServerCreator.fxml"),
                primaryStage
        );

        creator.setServerBuilder(this);
        creator.setOnPortChanged(param -> {
            //should be safe
            port = param;
        });
    }

    public void openAdvancedDialog() {
        //TODO: Server advanced dialog
    }

    public void host() {
        server.host();
    }
}
