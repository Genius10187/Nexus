package com.meti.app;

import com.meti.lib.io.client.ClientState;
import com.meti.lib.io.server.Server;
import com.meti.lib.io.server.command.Command;
import com.meti.lib.io.server.command.DisconnectCommand;
import com.meti.lib.util.Utility;
import com.meti.lib.util.fx.FXMLBundle;
import com.meti.lib.util.fx.StageableImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;

import static com.meti.app.Main.console;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2017
 */
public class ClientDisplay extends StageableImpl implements Initializable {
    private static final File serverDisplayLocation = new File(Main.resources, "ServerDisplay.fxml");
    private static final HashMap<String, File> viewFXMLMap = new HashMap<>();

    static {
        viewFXMLMap.put("Chat", new File(Main.resources, "Chat.fxml"));
        viewFXMLMap.put("Files", new File(Main.resources, "Files.fxml"));
        viewFXMLMap.put("Console", new File(Main.resources, "Console.fxml"));

        //properties are also settings
        viewFXMLMap.put("Properties", new File(Main.resources, "PropertyViewer.fxml"));
    }

    private final HashMap<String, Tab> currentViews = new HashMap<>();

    private ClientState state;

    @FXML
    private ListView<String> views;
    @FXML
    private TabPane viewPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        {
            views.getItems().addAll(viewFXMLMap.keySet());
            views.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    loadView(newValue);
                } catch (IOException e) {
                    console.log(Level.WARNING, e);
                }
            });
        }
    }

    private void loadView(String viewName) throws IOException {
        File file = viewFXMLMap.get(viewName);
        FXMLBundle bundle = Utility.load(file, EnumSet.of(Utility.FXML.NONE));
        Parent parent = bundle.getParent();
        Object controller = bundle.getController();
        if (parent != null && !currentViews.containsKey(viewName) && controller instanceof View) {
            View viewController = (View) controller;

            Tab tab = new Tab(viewName);
            tab.setContent(parent);

            viewPane.getTabs().add(tab);

            buildContentMenu(viewName, tab);
            currentViews.put(viewName, tab);

            viewController.setClientState(state);
            viewController.init();
        }
    }

    private void buildContentMenu(String viewName, Tab tab) {
        ContextMenu menu = new ContextMenu();

        MenuItem close = new MenuItem("Close");
        close.setOnAction(event -> closeView(viewName));

        MenuItem separateWindow = new MenuItem("Open in a Separate Window");
        separateWindow.setOnAction(event -> separateWindow(viewName));

        MenuItem[] menuItems = {
                separateWindow,
                close
        };

        menu.getItems().addAll(menuItems);
        tab.setContextMenu(menu);
    }

    private void separateWindow(String viewName) {
        Tab tab = currentViews.get(viewName);
        Node node = tab.getContent();

        if (node instanceof Parent) {
            tab.setContent(null);

            Scene scene = new Scene((Parent) node);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(event -> currentViews.remove(viewName));

            viewPane.getTabs().remove(tab);
        } else {
            throw new UnsupportedOperationException("Node " + node.getClass() + " is not instance of Parent");
        }
    }

    private void closeView(String viewName) {
        viewPane.getTabs().remove(currentViews.get(viewName));

        currentViews.remove(viewName);
    }

    public void setState(ClientState state) {
        this.state = state;
    }

    public void run() {
        console.log(Level.FINE, "Initializing controller");

        //TODO: change run
    }

    @FXML
    public void differentServer() {
        disconnect();

        try {
            load(Startup.connectFXML);
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }

    @FXML
    public void disconnect() {
        console.log(Level.FINE, "Disconnecting from server");

        try {
            Command disconnectCommand = new DisconnectCommand();

            state.getClient().write(disconnectCommand);
            state.getClient().close();

            console.log(Level.FINE, "Successfully disconnected from server");
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }

    @FXML
    public void openServerDisplay() {
        try {
            Server server = Startup.getState().getServer();
            ServerDisplay controller = (ServerDisplay) Utility.load(serverDisplayLocation, EnumSet.of(Utility.FXML.LOAD_STAGE)).getController();
            controller.setServer(server);
            controller.init(server.getState().getManager().getFiles());
        } catch (IOException e) {
            console.log(Level.WARNING, e);
        }
    }

    @FXML
    public void openProblem() {
        Main.getInstance().getHostServices().showDocument("https://github.com/Meticuli-Technologies/Nexus/issues/new");
    }

    @FXML
    public void openGitHub() {
        Main.getInstance().getHostServices().showDocument("https://github.com/Meticuli-Technologies/Nexus");
    }

    @FXML
    public void openWebsite() {
        Main.getInstance().getHostServices().showDocument("https://meticuli-technologies.github.io/Nexus/");
    }

    @FXML
    public void openDocumentation() {
        Main.getInstance().getHostServices().showDocument("https://meticuli-technologies.github.io/Nexus/javadocs/index.html");
    }
}
