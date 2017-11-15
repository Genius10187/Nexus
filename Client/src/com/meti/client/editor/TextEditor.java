package com.meti.client.editor;

import com.meti.client.Editor;

import java.io.File;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/14/2017
 */
public class TextEditor implements Editor {
    @Override
    public String[] getExtensions() {
        return new String[]{
                "txt"
        };
    }

    @Override
    public File getLocation() {
        return new File("Client\\assets\\fxml\\editor\\TextEditor.fxml");
    }
}
