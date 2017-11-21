package com.meti.client;

import com.meti.asset.Asset;
import javafx.stage.Stage;

import java.io.File;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/14/2017
 */
public abstract class Editor {
    private Stage stage;

    public void loadFXML() {

    }

    public abstract File getLocation();

    public abstract String[] getExtensions();

    public abstract void load(Asset asset);

    public abstract void change();

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
