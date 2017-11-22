package com.meti.io;

import com.meti.io.channel.Channel;
import com.meti.io.channel.in.InputChannelImplFactory;
import com.meti.io.channel.in.InputChannelImplFactory.InputChannelImpl;
import com.meti.io.channel.out.OutputChannelImplFactory;
import com.meti.io.channel.out.OutputChannelImplFactory.OutputChannelImpl;
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

    private final InputChannelImpl inputChannelImpl;
    private final OutputChannelImpl outputChannelImpl;

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

        this.inputChannelImpl = InputChannelImplFactory.create(parentOutputStream.getDefaultChannel());
        this.outputChannelImpl = OutputChannelImplFactory.create(parentOutputStream.getDefaultChannel());
    }

    @Override
    public <T> T read(Class<T> c) throws IOException, ClassNotFoundException {
        return inputChannelImpl.read(c);
    }

    @Override
    public Serializable read() throws IOException, ClassNotFoundException {
        return inputChannelImpl.read();
    }

    @Override
    public Serializable[] readAll() throws IOException, ClassNotFoundException {
        return inputChannelImpl.readAll();
    }

    @Override
    public void write(Serializable serializable) throws IOException {
        outputChannelImpl.write(serializable);
    }

    @Override
    public void writeAll(Serializable... serializables) throws IOException {
        outputChannelImpl.writeAll(serializables);
    }

    public Socket getSocket() {
        return socket;
    }

    public SplitObjectInputStream getParentInputStream() {
        return parentInputStream;
    }

    public SplitObjectOutputStream getParentOutputStream() {
        return parentOutputStream;
    }
}
