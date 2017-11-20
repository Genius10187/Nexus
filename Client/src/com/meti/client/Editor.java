package com.meti.client;

import com.meti.asset.Asset;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/14/2017
 */
public abstract class Editor {
    private Stage stage;

    public void loadFXML() throws IOException {

    }

    public abstract File getLocation();

    public abstract String[] getExtensions();

    public abstract void load(Asset asset);

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
