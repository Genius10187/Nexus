package com.meti.client;

import com.meti.asset.Asset;
import com.meti.io.Client;
import com.meti.util.Console;
import javafx.stage.Stage;

import java.io.File;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/14/2017
 */
public abstract class Editor {
    private Stage stage;
    protected Client client;
    protected Console console;

    public void loadFXML() {

    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public abstract File getLocation();

    public abstract String[] getExtensions();

    public abstract void load(Asset asset);

    public abstract void change();
}
