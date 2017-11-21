package com.meti.client.editor;

import com.meti.asset.Asset;
import com.meti.asset.text.TextChange;
import com.meti.client.Editor;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/14/2017
 */
public class TextEditor extends Editor {
    @FXML
    private TextArea output;

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
        ArrayList content = (ArrayList) asset.getContent();
        for (Object obj : content) {
            if (obj instanceof String) {
                output.appendText(obj + "\n");
            }
        }
    }

    @FXML
    @Override
    public void change() {
        //TODO: text change
        try {
            TextChange change = new TextChange();
            client.write(change);
        } catch (IOException e) {
            console.log(e);
        }
    }
}
