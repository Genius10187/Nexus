package com.meti.server;

import com.meti.util.Activator;
import com.meti.util.Loop;
import com.meti.util.Stoppable;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.logging.Level;

import static com.meti.Main.getInstance;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/20/2017
 */
public class Server implements Stoppable {
    private final ServerSocket serverSocket;

    private final List<Loop> connectionLoops = new ArrayList<>();
    private final List<Socket> sockets = new ArrayList<>();
    private final ListenLoop listenLoop = new ListenLoop(this);
    private final DisconnectLoop disconnectLoop = new DisconnectLoop();
    private final ClientExecutor executor = new ClientExecutor();

    private Activator<Socket> onClientConnect;
    private Activator<Socket> onClientDisconnect;
    private String password;

    private final File directory = new File("Nexus");

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void stop() {
        listenLoop.setRunning(false);

        for (Loop loop : connectionLoops) {
            loop.setRunning(false);
        }

        try {
            serverSocket.close();

            for (Socket socket : sockets) {
                socket.close();
            }
        } catch (IOException e) {
            getInstance().log(Level.SEVERE, e);
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void listen() {
        new Thread(listenLoop).start();
        new Thread(disconnectLoop).start();

        getInstance().log(Level.INFO, "Listening for clients");
    }

    public void setOnClientConnect(Activator<Socket> onClientConnect) {
        this.onClientConnect = onClientConnect;
    }

    public void setOnClientDisconnect(Activator<Socket> onClientDisconnect) {
        this.onClientDisconnect = onClientDisconnect;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public class DisconnectLoop extends Loop {
        @Override
        public void loop() {
            for (Socket socket : sockets) {
                if (socket.isClosed()) {
                    //theoretically, this should work
                    //even though socket has been closed, still has attributes
                    //not sure if this should be a safety concern
                    onClientDisconnect.activate(socket);
                    sockets.remove(socket);
                }
            }
        }
    }

    public class ListenLoop extends Loop {
        private final Server parent;

        public ListenLoop(Server parent) {
            this.parent = parent;
        }

        @Override
        public void loop() {
            try {
                //may throw a socket exception while the loop is listening
                Socket socket = serverSocket.accept();

                getInstance().log(Level.INFO, "Located client at " + socket.getInetAddress());

                sockets.add(socket);
                executor.execute(new ClientLoop(parent, socket));

                onClientConnect.activate(socket);
            } catch (SocketException e) {
                setRunning(false);
            } catch (IOException e) {
                getInstance().log(Level.WARNING, e);
            }
        }
    }

    public class ClientExecutor implements Executor {

        @Override
        public void execute(Runnable command) {
            if (command instanceof Loop) {
                connectionLoops.add((Loop) command);
            } else {
                getInstance().log(Level.WARNING, "Located object of not subtype Loop");
            }

            //make sure we execute the thread
            new Thread(command).start();
        }
    }
}
