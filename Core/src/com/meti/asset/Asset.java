package com.meti.asset;

import java.io.File;
import java.io.Serializable;

public class Asset implements Serializable {
    //TODO: handle assets from editors

    private final File location;

    //must be mutable
    private Serializable content;

    protected Asset(File location) {
        this.location = location;
    }

    public File getLocation() {
        return location;
    }

    public Serializable getContent() {
        return content;
    }

    public void setContent(Serializable content) {
        this.content = content;
    }
}
