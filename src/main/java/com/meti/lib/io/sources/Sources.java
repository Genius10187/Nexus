package com.meti.lib.io.sources;

import java.io.*;
import java.net.Socket;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class Sources {
    private Sources() {
    }

    public static BasicSource createBasicSource(File file) throws FileNotFoundException {
        return new BasicSource(new FileInputStream(file), new FileOutputStream(file));
    }

    public static BasicSource createBasicSource(Socket socket) throws IOException {
        return new BasicSource(socket.getInputStream(), socket.getOutputStream());
    }

    public static ObjectSource createObjectSource(File file) throws IOException {
        return new ObjectSource(new ObjectInputStream(new FileInputStream(file)), new ObjectOutputStream(new FileOutputStream(file)));
    }

    public static ObjectSource createObjectSource(Socket socket) throws IOException {
        return new ObjectSource(new ObjectInputStream(socket.getInputStream()), new ObjectOutputStream(socket.getOutputStream()));
    }

    public static BasicSource createBasicSource(URL url) throws IOException {
        return new BasicSource(url.openStream(), null);
    }

    public static BasicSource createBasicSource(InputStream inputStream, OutputStream outputStream) {
        return new BasicSource(inputStream, outputStream);
    }

    private static class BasicSource extends Source<InputStream, OutputStream> {
        public BasicSource(InputStream inputStream, OutputStream outputStream) {
            super(inputStream, outputStream);
        }
    }
}