package com.meti.app;

import com.meti.lib.io.client.ClientState;
import com.meti.lib.io.server.command.Argument;
import com.meti.lib.io.server.command.ListCommand;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/20/2017
 */
public class Files implements View {
    private final HashMap<File, TreeItem<String>> filePaths = new HashMap<>();

    @FXML
    private TreeView<String> fileView;

    private TreeItem<String> root = new TreeItem<>();
    private ClientState state;

    @Override
    public void setClientState(ClientState state) {
        this.state = state;
    }

    @Override
    public void init() {
        try {
            ListCommand command = new ListCommand(Argument.FILES);
            List<?> fileList = (List<?>) state.getClient().runCommand(command);
            for (Object o : fileList) {
                if (o instanceof File) {
                    createTreeItem((File) o);
                }
            }

            fileView.setRoot(root);
            fileView.setShowRoot(false);
        } catch (IOException e) {
            Main.console.log(Level.SEVERE, e);
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
}
