package com.meti.server;

import com.meti.server.asset.AssetChange;
import com.meti.server.util.Command;
import com.meti.util.Loop;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import static com.meti.Main.getInstance;
import static com.meti.util.Utility.castIfOfInstance;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2017
 */
public class ClientLoop extends Loop implements Sendable {
    private final Commander commander;

    private final Socket socket;
    private final ObjectInputStream input;
    private final ObjectOutputStream output;
    private final Server server;

    private final List<ClientLoop> clientLoops;

    public ClientLoop(Server server, Socket socket, ClientLoop... clientLoops) throws IOException {
        this.server = server;
        this.socket = socket;

        //we need to construct the output stream first
        //to discard the stream header
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());

        this.commander = new Commander(server, socket, this);

        this.clientLoops = Arrays.asList(clientLoops);
    }

    @Override
    public void loop() {
        try {
            try {
                Object next = input.readObject();
                String className = next.getClass().getName();

                if (className.equals("com.meti.server.util.Command")) {
                    commander.runCommand(castIfOfInstance(next, Command.class));
                } else if (next instanceof AssetChange) {
                    AssetChange assetChange = castIfOfInstance(next, AssetChange.class);
                    assetChange.update(server.getAssetManager().getAsset(assetChange.getAssetPath()));

                    //has been change, update all clientLoops
                    for (ClientLoop clientLoop : clientLoops) {
                        clientLoop.send(assetChange, true);
                    }

                } else {
                    getInstance().log(Level.WARNING, "Found no type handling " +
                            "for class type " + className);
                }
            } catch (EOFException e) {
                socket.close();
                setRunning(false);
            }
        } catch (Exception e) {
            getInstance().log(Level.WARNING, e);
        }
    }

    @Override
    public Serializable receive() throws IOException, ClassNotFoundException {
        return (Serializable) input.readObject();
    }

    @Override
    public void send(Serializable serializable, boolean flush) throws IOException {
        //learned something new today, caused so many problems...
        output.writeUnshared(serializable);
        if (flush) {
            output.flush();
        }
    }

    public List<ClientLoop> getClientLoops() {
        return clientLoops;
    }

    public Commander getCommander() {
        return commander;
    }
}