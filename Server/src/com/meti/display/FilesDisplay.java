package com.meti.display;

import com.meti.Server;
import com.meti.asset.AssetManager;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.util.HashMap;

public class FilesDisplay {
    @FXML
    private TreeView<String> filesView;

    public void setServer(Server server) {
        AssetManager manager = server.getAssetManager();
        ServerIndexer serverIndexer = new ServerIndexer();

        manager.getFiles().forEach(serverIndexer::index);

        filesView.setRoot(serverIndexer.getRoot());
        filesView.setShowRoot(false);
    }

    private class ServerIndexer {
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

        TreeItem<String> getRoot() {
            return root;
        }
    }
}
