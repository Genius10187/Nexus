package com.meti.util;

import java.util.HashMap;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/14/2018
 */
public class State<E extends Enum<?>, T> {
    private final HashMap<E, T> properties = new HashMap<>(0);

    public T setProperty(E key, T value) {
        return properties.put(key, value);
    }

    public T getProperty(E key) {
        return properties.get(key);
    }
}
