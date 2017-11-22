package com.meti.io;

import java.io.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/21/2017
 */
public class PipedStream {
    private final PipedInputStream inputStream;
    private final PipedOutputStream outputStream;

    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;

    public PipedStream() throws IOException {
        inputStream = new PipedInputStream();
        outputStream = new PipedOutputStream(inputStream);

        objectInputStream = new ObjectInputStream(inputStream);
        objectOutputStream = new ObjectOutputStream(outputStream);
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }
}
