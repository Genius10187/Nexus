package com.meti.client;

import com.meti.asset.Asset;
import com.meti.io.Client;
import com.meti.io.command.GetCommand;
import com.meti.io.command.ListCommand;
import com.meti.util.Console;
import com.meti.util.Utility;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/12/2017
 */
public class ClientDisplay implements Initializable {
    private final HashMap<String, Editor> editorMap = new HashMap<>();
    private final HashMap<String, Stage> editorStageMap = new HashMap<>();
    private final HashMap<File, TreeItem<String>> associations = new HashMap<>();
    @FXML
    private TreeView<String> fileView;
    @FXML
    private Text name;
    @FXML
    private Text size;
    @FXML
    private Text supported;
    private Console console;
    private Client client;
    private File currentFile;

    @FXML
    public void changeFile() {
        try {
            //TODO: multi file capability
            TreeItem<String> pathItem = fileView.getSelectionModel().getSelectedItems().get(0);
            File file = null;

            for (File f : associations.keySet()) {
                if (associations.get(f).equals(pathItem)) {
                    file = f;
                    break;
                }
            }

            if (file != null) {
                String name = client.requestCommand(new GetCommand(file, GetCommand.TYPE_PATH), String.class);
                this.name.setText(name);

                long size = client.requestCommand(new GetCommand(file, GetCommand.TYPE_SIZE), Long.class);
                this.size.setText(String.valueOf(size));

                supported.setText(String.valueOf(editorMap.containsKey(Utility.getExtension(file))));

                currentFile = file;
            }
        } catch (Exception e) {
            console.log(e);
        }
    }

    public void loadFromClientBuilder(ClientBuilder clientBuilder) throws IOException, ClassNotFoundException {
        this.console = clientBuilder.getConsole();
        this.client = clientBuilder.getClient();

        //occurs after initialization
        client.write(new ListCommand(ListCommand.TYPE_PATHS));

        //might return string here
        List<String> paths = client.readAll(String.class);

        ClientIndexer indexer = new ClientIndexer();
        for (String path : paths) {
            indexer.index(new File(path));
        }

        fileView.setRoot(indexer.getRoot());
        fileView.setShowRoot(false);
    }

    //A CHANGE
    //SOMETIMES WE NEED A REBASE GIT SCREWED UP AGAIN CURSE YOU WORLD
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
        try {
            String ext = Utility.getExtension(currentFile);

            Editor editor = editorMap.get(ext);
            editor.load(client.requestCommand(new GetCommand(currentFile, GetCommand.TYPE_VALUE), Asset.class));
            editorStageMap.get(ext).show();
        } catch (Exception e) {
            console.log(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<File> classFiles = Utility.search(new File("Client\\assets\\fxml\\editor"), "fxml");
            for (File classFile : classFiles) {
                FXMLLoader loader = new FXMLLoader(classFile.toURI().toURL());

                Parent parent = loader.load();
                Object controller = loader.getController();
                if (controller instanceof Editor) {
                    Editor editor = (Editor) controller;

                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();

                    stage.setScene(scene);
                    editor.setStage(stage);

                    for (String ext : editor.getExtensions()) {
                        editorMap.put(ext, editor);
                        editorStageMap.put(ext, stage);
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

        TreeItem<String> getRoot() {
            return root;
        }
    }
}
