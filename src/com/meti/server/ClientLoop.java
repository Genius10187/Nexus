package com.meti.server;

import com.meti.server.util.Command;
import com.meti.util.Loop;
import com.meti.util.Utility;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;

import static com.meti.Main.getInstance;

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

    public ClientLoop(Server server, Socket socket) throws IOException {
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

                switch (className) {
                    case "com.meti.server.util.Command":
                        commander.runCommand(Utility.castIfOfInstance(next, Command.class));
                        break;
                    default:
                        getInstance().log(Level.WARNING, "Found no type handling" +
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

    //hating on spell check once again :)
    public void sendAll(Serializable... serializables) throws IOException {
        //reduces excessive flushing
        for (Serializable serializable : serializables) {
            send(serializable, false);
        }

        output.flush();
    }

    public void send(Serializable serializable, boolean flush) throws IOException {
        output.writeObject(serializable);
        if (flush) {
            output.flush();
        }
    }
}