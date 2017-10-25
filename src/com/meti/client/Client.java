package com.meti.client;

import com.meti.server.util.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/22/2017
 */
public class Client {
    private final Socket socket;
    private final ObjectInputStream input;
    private final ObjectOutputStream output;

    public Client(InetAddress address, int port, String password) throws IOException {
        this.socket = new Socket(address, port);

        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());

        Command command = new Command("login", password);
        send(command, true);
    }

    public void send(Serializable serializable, boolean flush) throws IOException {
        output.writeObject(serializable);
        if (flush) {
            output.flush();
        }
    }

    public Object receive() throws IOException, ClassNotFoundException {
        return input.readObject();
    }
}
