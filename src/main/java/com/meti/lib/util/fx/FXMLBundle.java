package com.meti.lib.util.fx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2017
 */
public class FXMLBundle {
    private final FXMLLoader loader;
    private final Parent parent;
    private final Object controller;
    private final Stage stage;

    public FXMLBundle(FXMLLoader loader, Parent parent, Object controller, Stage stage) {
        this.loader = loader;
        this.parent = parent;
        this.controller = controller;
        this.stage = stage;

        if (controller instanceof Stageable) {
            ((Stageable) controller).setStage(stage);
        }
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public Parent getParent() {
        return parent;
    }

    public Object getController() {
        return controller;
    }

    public Stage getStage() {
        return stage;
    }
}
