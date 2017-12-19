package com.meti.lib.util.fx;

import com.meti.lib.util.Utility;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2017
 */
public class StageableImpl implements Stageable {
    protected Stage stage;

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public FXMLBundle load(URL url) throws IOException {
        return Utility.load(url, stage);
    }

    public FXMLBundle load(File file) throws IOException {
        return Utility.load(file, stage);
    }
}
