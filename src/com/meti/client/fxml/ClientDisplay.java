package com.meti.client.fxml;

import com.meti.Main;
import com.meti.client.Client;
import com.meti.server.asset.Asset;
import com.meti.server.util.Cargo;
import com.meti.server.util.Command;
import com.meti.util.Utility;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;

import static com.meti.util.Utility.getExtension;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/22/2017
 */
public class ClientDisplay implements Initializable {
    public static final Class[] editorClasses = {
            TextEditor.class
    };

    @FXML
    public TreeView<String> fileTreeView;

    private Client client;

    private HashMap<String, TreeItem<String>> localMap;
    private HashMap<String, Editor> editors = new HashMap<>();

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    public void openEditors() throws IOException, ClassNotFoundException {
        List<TreeItem<String>> items = fileTreeView.getSelectionModel().getSelectedItems();
        List<Asset<?>> assets = new ArrayList<>();

        for (TreeItem<String> item : items) {
            String path = getPathFromTreeItem(item);
            Command command = new Command("get", path);
            client.send(command, true);

            //maybe sending too many objects?

            Object receive = client.receive();
            Asset e = Utility.castIfOfInstance(receive, Asset.class);
            System.out.println("Receive: " + e);
            assets.add(e);
        }

        for (Asset<?> asset : assets) {
            String ext = getExtension(asset.getFile());
            editors.get(ext).load(asset, client);
        }
    }

    public String getPathFromTreeItem(TreeItem<String> item) {
        TreeItem<String> parent = item.getParent();
        if (parent == null || parent.getValue() == null) {
            return item.getValue();
        } else {
            return getPathFromTreeItem(parent) + "\\" + item.getValue();
        }
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
        localMap = new HashMap<>();

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Class c : editorClasses) {
            try {
                Editor instance = Utility.castIfOfInstance(c.newInstance(), Editor.class);
                instance.setClient(client);
                for (String ext : instance.getExtensions()) {
                    editors.put(ext, instance);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                Main.getInstance().log(Level.WARNING, e);
            }
        }
    }
}
