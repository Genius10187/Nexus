package com.meti.client;

import com.meti.util.Utility;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

public class ClientDisplay {
    //TODO: convert to string
    private final HashMap<File, TreeItem<String>> filePaths = new HashMap<>();
    private final HashMap<TreeItem<String>, File> filePathsReversed = new HashMap<>();

    @FXML
    private TabPane editorTabPane;
    @FXML
    private TreeView<String> fileTreeView;
    private Socket socket;
    private ClientHandler handler;
    private TreeItem<String> root = new TreeItem<>();

    @FXML
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Platform.exit();

        //concerning exit statement
        System.exit(0);
    }

    @FXML
    public void addEditor() {
        for (TreeItem<String> item : fileTreeView.getSelectionModel().getSelectedItems()) {
            File file = filePathsReversed.get(item);
            String ext = Utility.getExtension(file);
            System.out.println(ext);

            //TODO: add editor
        }
    }

    public void init() {
        List<File> filePathList = handler.getFilePaths();
        for (File filePath : filePathList) {
            createTreeItem(filePath);
        }

        for (File f : filePaths.keySet()) {
            filePathsReversed.put(filePaths.get(f), f);
        }

        fileTreeView.setRoot(root);
        fileTreeView.setShowRoot(false);
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

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setHandler(ClientHandler handler) {
        this.handler = handler;
    }
}
