package com.meti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Client {
    private final Commander commander = new Commander();
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final Socket socket;

    public Client(Socket socket) throws IOException {
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.socket = socket;
    }

    public List<? extends Serializable> readAll() throws IOException, ClassNotFoundException {
        int size = read();
        List<Serializable> objects = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Serializable serializable = read();
            objects.add(serializable);
        }

        return objects;
    }

    public <T> T read() throws IOException, ClassNotFoundException {
        //TODO: handle casts
        return (T) inputStream.readObject();
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
