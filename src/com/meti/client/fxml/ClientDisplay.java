package com.meti.client.fxml;

import com.meti.client.Client;
import com.meti.server.util.Cargo;
import com.meti.server.util.Command;
import com.meti.util.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/22/2017
 */
public class ClientDisplay {
    @FXML
    public TreeView<String> fileTreeView;

    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    public void openEditors() {

    }

    public void load() throws IOException, ClassNotFoundException {
        Command command = new Command("list", "files");
        client.send(command, true);

        Cargo<?> cargo = Utility.castIfOfInstance(client.receive(), Cargo.class);

        List<File> paths = new ArrayList<>();
        cargo.getContents().forEach((Consumer<Object>) o -> {
            paths.add(new File(Utility.castIfOfInstance(o, String.class)));
        });

        buildTree(paths);
    }

    public void buildTree(List<File> paths) {
        TreeItem<String> root = new TreeItem<>();
        HashMap<String, TreeItem<String>> localMap = new HashMap<>();

        for (File file : paths) {
            buildBranch(file, localMap, root);
        }

        fileTreeView.setRoot(root);
        fileTreeView.setShowRoot(false);
    }

    public void buildBranch(File file, HashMap<String, TreeItem<String>> localMap, TreeItem<String> root) {
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            TreeItem<String> item = new TreeItem<>(file.getName());
            localMap.put(file.getPath(), item);
            root.getChildren().add(item);
        } else {
            if (localMap.containsKey(parentFile.getPath())) {
                TreeItem<String> item = new TreeItem<>(file.getName());
                localMap.put(file.getPath(), item);
                localMap.get(parentFile.getPath()).getChildren().add(item);
            } else {
                buildBranch(parentFile, localMap, root);

                //we recall the file after looping
                buildBranch(file, localMap, root);
            }
        }
    }
}
