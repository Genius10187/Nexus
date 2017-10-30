package com.meti.server.asset;

import java.io.File;
import java.io.Serializable;

public class Asset<T extends Serializable> implements Serializable {
    private final File file;
    protected T content;

    public Asset(File file, T content) {
        this.file = file;
        this.content = content;
    }

    public Asset(File file) {
        this.file = file;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "file=" + file +
                ", content=" + content +
                '}';
    }
}
