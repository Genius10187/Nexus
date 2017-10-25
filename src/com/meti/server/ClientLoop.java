package com.meti.server;

import com.meti.server.asset.AssetChange;
import com.meti.server.util.Command;
import com.meti.util.Loop;

import java.io.*;
import java.net.Socket;
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

    public ClientLoop(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;

        //we need to construct the output stream first
        //to discard the stream header
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());

        this.commander = new Commander(server, socket, this);
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

    public void send(Serializable serializable, boolean flush) throws IOException {
        output.writeObject(serializable);
        if (flush) {
            output.flush();
        }
    }
}