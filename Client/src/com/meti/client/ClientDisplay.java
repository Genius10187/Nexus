package com.meti.client;

import com.meti.io.Client;
import com.meti.io.Command;
import com.meti.util.Console;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/12/2017
 */
public class ClientDisplay {
    private final HashMap<String, Editor> editorMap = new HashMap<>();

    @FXML
    private TreeView<String> fileView;

    @FXML
    private Tooltip editToolTip;

    @FXML
    private ListView<String> editorOptions;

    @FXML
    private GridPane fileDataPane;

    @FXML
    private Text name;

    @FXML
    private Text size;

    @FXML
    private Text supported;

    private ClientBuilder clientBuilder;
    private Console console;
    private Client client;

    private HashMap<File, TreeItem<String>> associations = new HashMap<>();
    private File currentFile;

    //TODO: create editor files

    @FXML
    public void changeFile() {
        try {
            TreeItem<String> pathItem = fileView.getSelectionModel().getSelectedItems().get(0);
            File file = null;

            for (File f : associations.keySet()) {
                if (associations.get(f).equals(pathItem)) {
                    file = f;
                    break;
                }
            }

            if (file != null) {
                client.write(new Command("get", "size", file.getPath()));
            }
        } catch (IOException e) {
            console.log(e);
        }
    }

    public void loadFromClientBuilder(ClientBuilder clientBuilder) throws IOException, ClassNotFoundException {
        this.clientBuilder = clientBuilder;
        this.console = clientBuilder.getConsole();
        this.client = clientBuilder.getClient();

        //occurs after initialization
        client.write(new Command("list", "paths"));

        //might return string here
        List<String> paths = client.readAll(String.class);

        ClientIndexer indexer = new ClientIndexer();
        for (String path : paths) {
            indexer.index(new File(path));
        }

        fileView.setRoot(indexer.getRoot());
        fileView.setShowRoot(false);
    }

    @FXML
    public void openToolTip() {
        //do something here
    }

    @FXML
    public void sendReport() {
        //TODO: send report
    }

    @FXML
    public void close() {
        Platform.exit();
    }

    @FXML
    public void openEditors() {
    }

    private class ClientIndexer {
        private final TreeItem<String> root = new TreeItem<>();

        private void index(File file) {
            File parent = file.getParentFile();

            if (parent == null) {
                TreeItem<String> item = new TreeItem<>(file.getName());
                root.getChildren().add(item);

                associations.put(file, item);
            } else if (associations.containsKey(parent)) {
                TreeItem<String> item = new TreeItem<>(file.getName());
                associations.get(parent).getChildren().add(item);

                associations.put(file, item);
            } else {

                //we seriously don't need to do more copy and pasting, do we?
                index(parent);
                index(file);
            }
        }

        public TreeItem<String> getRoot() {
            return root;
        }
    }
}
