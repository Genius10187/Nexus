package com.meti.server.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cargo<T> implements Serializable {
    private final List<T> contents;

    public Cargo() {
        this.contents = new ArrayList<>();
    }

    public Cargo(List<T> contents) {
        this.contents = contents;
    }

    public Class<?> getGenericType() {
        return contents.get(0).getClass();
    }

    public List<T> getContents() {
        return contents;
    }
}
