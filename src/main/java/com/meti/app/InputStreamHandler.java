package com.meti.app;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/20/2017
 */
public class InputStreamHandler extends StreamHandler {
    private final PipedInputStream inputStream = new PipedInputStream();

    public InputStreamHandler() throws IOException {
        PipedOutputStream outputStream = new PipedOutputStream();
        inputStream.connect(outputStream);

        setOutputStream(outputStream);
        setFormatter(new SimpleFormatter());
    }

    public PipedInputStream getInputStream() {
        return inputStream;
    }
}
