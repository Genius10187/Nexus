package com.meti.client.editor;

import com.meti.client.Editor;

import java.io.File;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/14/2017
 */
public class ImageEditor implements Editor {
    @Override
    public String[] getExtensions() {
        //there's a whole bunch of them
        return new String[]{
                "jpeg",
                "jpg",
                "img",
                "png"
        };
    }

    @Override
    public File getLocation() {
        return new File("Client\\assets\\fxml\\editor\\ImageEditor.fxml");
    }
}
