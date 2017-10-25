package com.meti.server;

import com.meti.server.util.Cargo;
import com.meti.server.util.Command;
import com.meti.util.Loop;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;

import static com.meti.Main.getInstance;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2017
 */
public class ClientLoop extends Loop {
    private final Server server;
    private final Socket socket;
    private final ObjectInputStream input;
    private final ObjectOutputStream output;

    public ClientLoop(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;

        //we need to construct the output stream first
        //to discard the stream header
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void loop() {
        try {
            try {
                Object next = input.readObject();
                String className = next.getClass().getName();

                switch (className) {
                    case "com.meti.server.util.Command":
                        runCommand((Command) next);
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

    private void runCommand(Command next) throws IOException {
        switch (next.getName()) {
            case "login":
                String password = (String) next.getArgs()[0];
                if (password.equals(server.getPassword())) {
                    getInstance().log(Level.INFO, "Client " + socket.getInetAddress() + " has connected with valid password");
                } else {
                    getInstance().log(Level.INFO, "Client has invalid password, kicking out!");
                    socket.close();
                }
                break;
            case "disconnect":
                socket.close();
                break;
            case "list":
                Cargo cargo = new Cargo<>();
                //forgot to do assetManager, hold on
                break;
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