package com.meti.asset;

import java.io.File;

public class Asset {
    private final File location;

    //must be mutable
    private Object content;

    public Asset(File location) {
        this.location = location;
    }

    public File getLocation() {
        return location;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
