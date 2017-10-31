package com.meti.client;

import com.meti.server.Sendable;
import com.meti.server.asset.AssetChange;
import com.meti.server.util.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/22/2017
 */
public class Client implements Sendable {
    private final List<AssetChange> changes = new ArrayList<>();

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

    @Override
    public Serializable receive() throws IOException, ClassNotFoundException {
        Object obj = input.readObject();
        if (obj instanceof AssetChange) {
            changes.add((AssetChange) obj);

            return receive();
        } else {
            return (Serializable) obj;
        }
    }

    public void send(Serializable serializable, boolean flush) throws IOException {
        output.writeUnshared(serializable);
        if (flush) {
            output.flush();
        }
    }

    public List<AssetChange> getChanges() {
        return changes;
    }
}
