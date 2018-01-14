package com.meti.util;

import javafx.scene.Parent;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/14/2018
 */
public class FXMLBundle<T> {
    private final Object source;

    private final Parent parent;
    private final T controller;

    public FXMLBundle(Object source, Parent parent, T controller) {
        this.source = source;
        this.parent = parent;
        this.controller = controller;
    }

    public Object getSource() {
        return source;
    }

    public Parent getParent() {
        return parent;
    }

    public T getController() {
        return controller;
    }
}
