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

/**
 * A Client object specifies a unique handling to transfer information across sockets.
 */
public class Client {
    //should the type be of PriorityQueue?
    private final PriorityQueue<Object> inputBuffer = new PriorityQueue<>();
    private final Commander commander = new Commander();
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final Socket socket;

    /**
     * <p>
     * Constructs a new Client specified by the socket parameter.
     * Then constructs both an ObjectInputStream and ObjectOutputStream
     * </p>
     *
     * @param socket The socket
     * @throws IOException Thrown during construction of either stream
     * @see ObjectInputStream
     * @see ObjectOutputStream
     */
    public Client(Socket socket) throws IOException {
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.socket = socket;
    }

    /**
     * <p>
     * Reads a defined number of objects specified by an incoming integer, which represents the size.
     * Then returns an ArrayList composed of all objects read of type T.
     * Uses {@link #read(Class)}
     * </p>
     *
     * @param c   The class with type T
     * @param <T> The type of the object to receive
     * @return The objects read of type T
     * @throws IOException            See {@link #read(Class)}
     * @throws ClassNotFoundException See {@link #read(Class)}
     */
    public <T extends Serializable> List<T> readAll(Class<T> c) throws IOException, ClassNotFoundException {
        int size = read(Integer.class);
        List<T> objects = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            T serializable = read(c);
            objects.add(serializable);
        }

        return objects;
    }

    /**
     * <p>
     * Reads a single object specified by T.
     * The parameter c must be specified the same as type T.
     * If the input buffer contains an object of type T,
     * that object will be returned instead of reading a new one.
     * If the input buffer does not contain an object of type T,
     * then the ObjectInputStream will read a new object.
     * Any other objects not of type T that appear before the object of type T
     * will be added to the input buffer.
     * A ClassNotFoundException is thrown when the class specified by the parameter c
     * does not exist. This exception should never be thrown, unless the class specified
     * is not accessible in runtime.
     * </p>
     *
     * @param c   The class with type T
     * @param <T> The type of the object receive
     * @return The next object of type T
     * @throws IOException            See {@link ObjectInputStream#readObject()}
     * @throws ClassNotFoundException If the class specified by T does not exist
     */
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

    /**
     * <p>
     * Writes all objects specified by the parameters.
     * First, the size of the objects is written.
     * Next, each object is written.
     * Then the stream is flushed.
     * Should be used in conjunction with {@link #readAll(Class)}.
     * </p>
     *
     * @param objects The objects to be written.
     * @throws IOException {@link java.io.ObjectOutputStream#writeUnshared(Object)}
     */
    public void writeAll(Collection<? extends Serializable> objects) throws IOException {
        int size = objects.size();

        outputStream.writeUnshared(size);
        for (Serializable serializable : objects) {
            outputStream.writeUnshared(serializable);
        }

        outputStream.flush();
    }

    /**
     * <p>
     * Writes an object through the socket.
     * The object must implement serializable,
     * such that serialization can occur.
     * The stream is then flushed.
     * <p>
     * See also {@link java.io.ObjectOutputStream#writeUnshared(Object)}
     * </p>
     *
     * @param serializable The object
     * @throws IOException If an error occurred
     */
    public void write(Serializable serializable) throws IOException {
        outputStream.writeUnshared(serializable);
        outputStream.flush();
    }

    /**
     * Returns the socket
     *
     * @return The socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * {@link Commander#run(Command)}
     *
     * @param command The command
     */
    public void run(Command command) {
        commander.run(command);
    }

    //TODO: write commander
    private class Commander {
        public void run(Command command) {

        }
    }
}
