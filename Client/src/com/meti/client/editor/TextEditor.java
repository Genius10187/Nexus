package com.meti.client.editor;

import com.meti.client.Editor;

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
}
