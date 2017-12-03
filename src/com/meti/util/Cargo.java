package com.meti.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/3/2017
 */
public class Cargo implements Serializable {
    private final List<Object> contents;

    public Cargo(Collection<?> contents) {
        this();

        this.contents.addAll(contents);
    }

    public Cargo() {
        this.contents = new ArrayList<>();
    }

    public Cargo(Object... contents) {
        this();
        this.contents.addAll(Arrays.asList(contents));
    }

    public List<Object> getContents() {
        return contents;
    }
}
