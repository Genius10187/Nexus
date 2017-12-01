package com.meti.asset;

import java.io.Serializable;

public class Asset implements Serializable {
    private Object content;

    public Asset() {
    }

    public Asset(Object content) {
        this.content = content;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
