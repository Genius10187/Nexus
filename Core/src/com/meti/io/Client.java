package com.meti.io;

import com.meti.io.channel.Channel;
import com.meti.io.channel.ChannelFactory;
import com.meti.io.channel.in.InputChannelImplFactory;
import com.meti.io.channel.in.InputChannelImplFactory.InputChannelImpl;
import com.meti.io.channel.out.OutputChannelImplFactory;
import com.meti.io.channel.out.OutputChannelImplFactory.OutputChannelImpl;
import com.meti.io.command.Command;
import com.meti.io.split.SplitObjectInputStream;
import com.meti.io.split.SplitObjectOutputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

/**
 * A Client object specifies a unique handling to transfer information across sockets.
 */
public class Client {
    private final Socket socket;
    //TODO: put splitObjectInputStream here
    private final SplitObjectInputStream parentInputStream;
    private final SplitObjectOutputStream parentOutputStream;

    private final HashMap<Class<?>, Channel> channelHashMap = new HashMap<>();
    private final ExecutorService executorService;

    /**
     * <p>
     * Constructs a new Client specified by the socket parameter.
     * First, assigns the socket to the client.
     * Then constructs both an ObjectInputStream and ObjectOutputStream
     * </p>
     *
     * @param socket          The socket
     * @param executorService
     * @throws IOException Thrown during construction of either stream
     * @see ObjectInputStream
     * @see ObjectOutputStream
     */
    public Client(Socket socket, ExecutorService executorService) throws IOException {
        this.socket = socket;

        //10 / 10 should work
        this.parentOutputStream = new SplitObjectOutputStream(new ObjectOutputStream(socket.getOutputStream()));
        this.parentInputStream = new SplitObjectInputStream(new ObjectInputStream(socket.getInputStream()));

        this.executorService = executorService;
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

    public void listen() {
        executorService.submit(parentInputStream.getRunnable());
    }

    public <T> T requestCommand(Command command, Class<T> assetClass) throws IOException, ClassNotFoundException {
        Channel commandChannel = getChannel(Command.class);

        commandChannel.write(command);
        return commandChannel.read(assetClass);
    }

    public Channel getChannel(Class<?> c) throws IOException {
        if (channelHashMap.containsKey(c)) {
            return channelHashMap.get(c);
        } else {
            InputChannelImpl inputChannel = InputChannelImplFactory.create(parentInputStream.forClass(c));
            OutputChannelImpl outputChannel = OutputChannelImplFactory.create(parentOutputStream.forClass(c, executorService));

            Channel channel = ChannelFactory.createChannel(inputChannel, outputChannel);
            channelHashMap.put(c, channel);

            return channel;
        }
    }
}
