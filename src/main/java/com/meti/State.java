package com.meti;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/30/2017
 */
public class State {
    private final Properties properties = new Properties();

    public Object setProperty(String key, String value) {
        return properties.setProperty(key, value);
    }

    public void load(InputStream inStream) throws IOException {
        properties.load(inStream);
    }

    public void store(OutputStream out, String comments) throws IOException {
        properties.store(out, comments);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public int size() {
        return properties.size();
    }

    public boolean isEmpty() {
        return properties.isEmpty();
    }

    public boolean contains(Object value) {
        return properties.contains(value);
    }
}
