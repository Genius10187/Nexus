package com.meti.lib.io.sources;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class ObjectSource extends Source<ObjectInputStream, ObjectOutputStream> {
    public ObjectSource(ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        super(inputStream, outputStream);
    }
}
