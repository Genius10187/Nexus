package com.meti.util;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/11/2017
 */
public class Buffer<T extends Serializable> implements Serializable {
    private final Serializable[] values;

    public Buffer(int... dims) {
        if (dims != null && dims.length != 0) {
            values = new Serializable[dims[0]];

            if (dims.length > 1) {
                for (int i = 0; i < values.length; i++) {
                    values[i] = new Buffer(Arrays.copyOfRange(values, 1, values.length));
                }
            }
        } else {
            throw new IllegalArgumentException("Dimensions cannot be zero");
        }
    }

    public Buffer(int size) {
        this.values = new Serializable[size];
    }

    public Buffer(Serializable[] values) {
        this.values = values;
    }

    public void put(int index, T obj) {
        values[index] = obj;
    }

    public T get(int index) {
        return (T) values[index];
    }

    public int size() {
        return values.length;
    }
}
