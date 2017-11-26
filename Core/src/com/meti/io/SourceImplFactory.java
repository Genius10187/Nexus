package com.meti.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/25/2017
 */
public class SourceImplFactory {
    private SourceImplFactory() {
    }

    public static SourceImpl create(Socket socket) throws IOException {
        return new SourceImpl(socket.getInputStream(), socket.getOutputStream());
    }

    public static class SourceImpl implements Source {
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SourceImpl(InputStream inputStream, OutputStream outputStream) {
            this.inputStream = inputStream;
            this.outputStream = outputStream;
        }

        @Override
        public InputStream getInputStream() {
            return inputStream;
        }

        @Override
        public OutputStream getOutputStream() {
            return outputStream;
        }
    }
}
