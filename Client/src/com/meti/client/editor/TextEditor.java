package com.meti.client.editor;

import com.meti.asset.Asset;
import com.meti.client.Editor;

import java.io.File;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/14/2017
 */
public class TextEditor extends Editor {
    @Override
    public File getLocation() {
        return new File("Client\\assets\\fxml\\editor\\TextEditor.fxml");
    }

    @Override
    public String[] getExtensions() {
        return new String[]{
                "txt"
        };
    }

    @Override
    public void load(Asset asset) {

    }
}
