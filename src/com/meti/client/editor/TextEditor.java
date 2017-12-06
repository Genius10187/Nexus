package com.meti.client.editor;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.File;

public class TextEditor implements Editor {
    @FXML
    private TextArea output;

    @Override
    public String[] getExtensions() {
        return new String[]{
                "txt"
        };
    }

    @Override
    public void load(File location) {
        //TODO: handle textEditor file
    }
}
