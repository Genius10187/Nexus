package com.meti.lib.io.sources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class Source<A extends InputStream, B extends OutputStream> {
    private final A inputStream;
    private final B outputStream;

    public Source(A inputStream, B outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public A getInputStream() {
        return inputStream;
    }

    public B getOutputStream() {
        return outputStream;
    }

    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
    }
}
