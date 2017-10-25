package com.meti.server.asset;

import java.io.Serializable;

public class Asset<T extends Serializable> implements Serializable {
    private T content;

    public Asset(T content) {
        this.content = content;
    }

    public Asset() {
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
