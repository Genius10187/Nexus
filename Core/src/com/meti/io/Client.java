package com.meti.io;

import com.meti.util.Utility;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;

public class Client {
    private final PriorityQueue<Object> inputBuffer = new PriorityQueue<>();
    private final Commander commander = new Commander();
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final Socket socket;

    public Client(Socket socket) throws IOException {
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.socket = socket;
    }

    public <T extends Serializable> List<T> readAll(Class<T> c) throws IOException, ClassNotFoundException {
        int size = read(Integer.class);
        List<T> objects = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            T serializable = read(c);
            objects.add(serializable);
        }

        return objects;
    }

    public <T> T read(Class<T> c) throws IOException, ClassNotFoundException {
        T obj = null;

        for (Object o : inputBuffer) {
            T result = Utility.castIfOfInstance(o, c);
            if (result != null) {
                obj = result;
                break;
            }
        }

        while (obj == null) {
            Object nextToken = inputStream.readObject();
            T result = Utility.castIfOfInstance(nextToken, c);
            if (result != null) {
                obj = result;
                break;
            } else {
                inputBuffer.add(nextToken);
            }
        }
        return obj;
    }

    public void writeAll(Collection<? extends Serializable> objects) throws IOException {
        int size = objects.size();

        outputStream.writeObject(size);
        for (Serializable serializable : objects) {
            outputStream.writeObject(serializable);
        }

        outputStream.flush();
    }

    public void write(Serializable serializable) throws IOException {
        outputStream.writeObject(serializable);
        outputStream.flush();
    }

    public Socket getSocket() {
        return socket;
    }

    public void run(Command command) {
        commander.run(command);
    }

    private class Commander {
        public void run(Command command) {

        }
    }
}
