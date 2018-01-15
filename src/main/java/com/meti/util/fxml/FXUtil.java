package com.meti.util.fxml;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

import static javafx.scene.layout.AnchorPane.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/14/2018
 */
public class FXUtil {
    private FXUtil() {
    }

    //methods
    public static void zeroAnchors(Node node) {
        setTopAnchor(node, 0d);
        setRightAnchor(node, 0d);
        setBottomAnchor(node, 0d);
        setLeftAnchor(node, 0d);
    }

    public static <T> FXMLBundle<T> load(URL url) throws IOException {
        FXMLLoader loader = new FXMLLoader(url);
        Parent parent = loader.load();
        T controller = loader.getController();
        return new FXMLBundle<>(url, parent, controller);
    }
}