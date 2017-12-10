package com.meti.client.editor;

import java.io.File;

public interface Editor {
    String[] getExtensions();

    void load(File location);
}
