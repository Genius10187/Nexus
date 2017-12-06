package com.meti.client;

import com.meti.client.editor.Editor;
import com.meti.util.Utility;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class ClientDisplay implements Initializable {
    private final HashMap<String, File> editorFileMap = new HashMap<>();

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
            File location = filePathsReversed.get(item);
            String ext = Utility.getExtension(location);
            System.out.println(ext);

            //TODO: add editor
            try {
                File editorFile = editorFileMap.get(ext);
                FXMLLoader loader = new FXMLLoader(editorFile.toURI().toURL());
                Parent parent = loader.load();

                //careful here
                Editor editor = loader.getController();
                editor.load(location);

                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                handler.getLogger().log(Level.WARNING, "Error occured when creating editor: " + e.toString());
            }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File directoryFile = new File("assets\\fxml\\editor");
        List<File> fileList = Utility.search(directoryFile, "fxml");
        for (File file : fileList) {
            try {
                FXMLLoader loader = new FXMLLoader(file.toURI().toURL());
                Parent parent = loader.load();
                Object controller = loader.getController();
                Editor editor = (Editor) controller;

                if (parent != null) {
                    for (String ext : editor.getExtensions()) {
                        editorFileMap.put(ext, file);
                    }
                } else {
                    throw new RuntimeException("Parent cannot be null in an fxml file!");
                }
            } catch (Exception e) {
                handler.getLogger().log(Level.WARNING, e.toString());
            }
        }
    }
}
