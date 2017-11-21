package com.meti.io;

import com.meti.io.channel.Channel;
import com.meti.io.channel.InputChannelImplFactory;
import com.meti.io.split.SplitObjectInputStream;
import com.meti.io.split.SplitObjectOutputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * A Client object specifies a unique handling to transfer information across sockets.
 */
public class Client implements Channel {
    private final Socket socket;
    //TODO: put splitObjectInputStream here
    private final SplitObjectInputStream parentInputStream;
    private final SplitObjectOutputStream parentOutputStream;

    private final InputChannelImplFactory.InputChannelImpl inputChannelImpl;

    /**
     * <p>
     * Constructs a new Client specified by the socket parameter.
     * First, assigns the socket to the client.
     * Then constructs both an ObjectInputStream and ObjectOutputStream
     * </p>
     *
     * @param socket The socket
     * @throws IOException Thrown during construction of either stream
     * @see ObjectInputStream
     * @see ObjectOutputStream
     */
    public Client(Socket socket) throws IOException {
        this.socket = socket;

        //10 / 10 should work
        this.parentInputStream = new SplitObjectInputStream(new ObjectInputStream(socket.getInputStream()));
        this.parentOutputStream = new SplitObjectOutputStream(new ObjectOutputStream(socket.getOutputStream()));

        //TODO: handle channel creation here
        this.inputChannelImpl = InputChannelImplFactory.create();
    }

    @Override
    public <T> T read(Class<T> c) {
        return null;
    }

    @Override
    public Serializable read() {
        return null;
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

    /**
     * Returns the socket
     *
     * @return The socket
     */
    public Socket getSocket() {
        return socket;
    }

    @Override
    public Serializable[] readAll() {
        return new Serializable[0];
    }

    @Override
    public void write(Serializable serializable) {

    }

    @Override
    public void write(Serializable... serializables) {

    }

    public SplitObjectInputStream getParentInputStream() {
        return parentInputStream;
    }

    public SplitObjectOutputStream getParentOutputStream() {
        return parentOutputStream;
    }
}
