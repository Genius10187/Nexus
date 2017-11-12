package com.meti.app;

import com.meti.Server;
import com.meti.display.ServerDisplay;
import com.meti.util.Console;
import com.meti.util.Utility;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/7/2017
 */
public class ServerBuilder {
    private final Console console;

    private Server server;

    private String localAddressToken;
    private String maxQueueSizeToken;
    private String portToken;

    //default field values
    public ServerBuilder(Console console) {
        this.console = console;

        //may run into some bugs here...
        localAddressToken = null;
        maxQueueSizeToken = "50";
        portToken = "0";
    }

    //we have primaryStage here because this is the first fxml loaded by the main class
    public void openDefaultDialog(Stage primaryStage) throws IOException {
        console.log(Level.FINE, "Opening default dialog");

        ServerCreator creator = Utility.buildFXML(
                new File("Server\\assets\\fxml\\ServerCreator.fxml"),
                primaryStage
        );

        creator.setServerBuilder(this);

        //should be safe
        creator.setOnPortChanged(param -> portToken = param);
    }

    public void openAdvancedDialog() throws IOException {
        ServerAdvancedCreator creator = Utility.buildFXML(
                new File("Server\\assets\\fxml\\ServerAdvancedCreator.fxml"),
                null
        );

        creator.setServerBuilder(this);

        //see app.setOnPortChanged for possible problems
        creator.setOnMaxQueueSizeChanged(param -> maxQueueSizeToken = param);
        creator.setOnLocalAddress(param -> localAddressToken = param);
    }

    public void host() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (server == null) {
            buildServer();
        }

        //helpful information
        console.log("Hosting the server at " + server.getServerSocket().getInetAddress());


        server.host();

        ServerDisplay display = Utility.buildFXML(new File("Server\\assets\\fxml\\ServerDisplay.fxml"), null);
        display.setServer(server);
    }

    private void buildServer() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        int port = Integer.parseInt(portToken);
        int maxQueueSize = Integer.parseInt(maxQueueSizeToken);
        InetAddress localAddress = InetAddress.getByName(localAddressToken);

        if (localAddressToken != null) {
            server = new Server(port, maxQueueSize, localAddress, console);
        } else {
            server = new Server(port, maxQueueSize, console);
        }
    }

    public Console getConsole() {
        return console;
    }

    public Server getServer() {
        return server;
    }
}
