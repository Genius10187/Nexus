package com.meti.client;

import com.meti.io.Client;
import com.meti.io.Command;
import com.meti.util.Console;
import com.meti.util.Utility;
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

    {
        try {
            List<File> classFiles = Utility.search(new File("Core"), "java");
            for (File classFile : classFiles) {
                Class<?> c = Utility.getClassFromFile(new File("Core\\src"), classFile);
                if (Editor.class.isAssignableFrom(c) && !c.getName().equals("com.meti.client.Editor")) {
                    Editor instance = (Editor) c.newInstance();
                    String[] extensions = instance.getExtensions();
                    for (String ext : extensions) {
                        editorMap.put(ext, instance);
                    }
                }
            }
        } catch (Exception e) {
            if (console != null) {
                console.log(e);
            } else {
                e.printStackTrace();
            }
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
        //TODO: index editors
    }

    private class ClientIndexer {
        private final HashMap<File, TreeItem<String>> associations = new HashMap<>();
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
