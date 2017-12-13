package com.meti.util;

import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * An FXBundle allows for a parent node and a controller to be embedded in the same class.
 */
public class FXBundle {
    private final Parent parent;
    private final Object controller;
    private final Stage stage;

    /**
     * Constructs an FXBundle with a parent, controller, and stage.
     *
     * @param parent     The parent.
     * @param controller The controller.
     * @param stage      The stage.
     */
    public FXBundle(Parent parent, Object controller, Stage stage) {
        this.parent = parent;
        this.controller = controller;
        this.stage = stage;
    }

    /**
     * Gets the parent.
     *
     * @return The parent.
     */
    public Parent getParent() {
        return parent;
    }

    /**
     * Gets the controller.
     *
     * @return The controller.
     */
    public Object getController() {
        return controller;
    }

    /**
     * Gets the stage.
     *
     * @return The stage.
     */
    public Stage getStage() {
        return stage;
    }
}
