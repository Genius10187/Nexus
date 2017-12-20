package com.meti.lib.io.server.asset;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class Asset<T> {
    private T[] content;

    public Asset(T[] content) {
        this.content = content;
    }

    public T[] getContent() {
        return content;
    }

    public Class getContentClass() {
        return content.getClass();
    }
}
