package com.meti.app;

import com.meti.lib.io.server.Server;
import com.meti.lib.util.fx.StageableImpl;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/22/2017
 */
public class ServerDisplay extends StageableImpl implements Initializable {
    private final HashMap<File, TreeItem<String>> filePaths = new HashMap<>();

    @FXML
    private ListView<String> clientView;

    @FXML
    private TextArea console;

    @FXML
    private TextArea chat;

    @FXML
    private TreeView<String> fileView;

    private TreeItem<String> root = new TreeItem<>();
    private Server server;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileView.setRoot(root);
        fileView.setShowRoot(false);
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @FXML
    public void shutdown() {
        Platform.exit();
    }

    @FXML
    public void addFiles() {
        try {
            DirectoryChooser chooser = new DirectoryChooser();
            File returned = chooser.showDialog(stage);

            List<File> loadedFiles = server.getState().getManager().load(returned);
            for (Object o : loadedFiles) {
                if (o instanceof File) {
                    createTreeItem((File) o);
                }
            }

            server.getState().getConsole().log(Level.FINE, "Loaded " + loadedFiles.size() + " files");
        } catch (IOException e) {
            server.getState().getConsole().log(Level.WARNING, e);
        }
    }

    private void createTreeItem(File file) {
        //never create this file, only there for utility methods
        File parentFile = file.getParentFile();

        TreeItem<String> item = new TreeItem<>();
        item.setValue(file.getName());

        if (parentFile != null) {
            if (filePaths.containsKey(parentFile)) {
                filePaths.get(parentFile).getChildren().add(item);
                filePaths.put(file, item);
            } else {
                //probably could replace this with file.getParent()
                createTreeItem(parentFile);
                createTreeItem(file);
            }
        } else {
            root.getChildren().add(item);
            filePaths.put(file, item);
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

    public void init(List<File> loadedFiles) {
        //files
        for (Object o : loadedFiles) {
            if (o instanceof File) {
                createTreeItem((File) o);
            }
        }

        server.getState().getConsole().log(Level.FINE, "Initialized " + loadedFiles.size() + " files");

        //console
        Handler handler = new Handler() {
            @Override
            public void publish(LogRecord record) {
                console.appendText(getFormatter().format(record) + " ");
            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        };
        handler.setFormatter(new SimpleFormatter());
        handler.setLevel(Level.ALL);
        server.getState().getConsole().addHandler(handler);
    }
}
