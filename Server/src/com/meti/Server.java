package com.meti;

import com.meti.asset.AssetChange;
import com.meti.asset.AssetManager;
import com.meti.io.Client;
import com.meti.io.channel.Channel;
import com.meti.io.command.Command;
import com.meti.util.Action;
import com.meti.util.Console;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/7/2017
 */
public class Server {
    private static final long TIME_FOR_INSTANT_SHUTDOWN = 5000;

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final AssetManager assetManager;
    private final ServerSocket serverSocket;
    private final Console console;

    private final ArrayList<Client> clients = new ArrayList<>();
    private Action<Client> onClientDisconnect;
    private Action<Client> onClientConnect;

    public Server(int port, int maxQueueSize, Console console) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        this(port, maxQueueSize, null, console);
    }

    /*
    maxQueueSize and backlog are the same thing
    it specifies how many sockets can connect to
    the server at once, i.e. user limit
    */
    public Server(int port, int maxQueueSize, InetAddress address, Console console) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (address != null) {
            this.serverSocket = new ServerSocket(port, maxQueueSize, address);
        } else {
            this.serverSocket = new ServerSocket(port, maxQueueSize);
        }

        File directory = new File("Nexus");
        if (directory.mkdirs()) {
            console.log("Created server directory");
        }

        this.assetManager = new AssetManager(console);
        this.assetManager.read(directory);

        this.console = console;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void host() {
        executorService.submit(new ClientListener());
    }

    public void stop() throws IOException, InterruptedException {
        console.log("Disconnecting clients");
        clients.forEach(client -> {
            try {
                client.getSocket().close();
            } catch (IOException e) {
                console.log(Level.SEVERE, e);
            }
        });

        console.log("Shutting down thread executorService");
        executorService.shutdown();

        console.log("Shutting down server socket");
        serverSocket.close();

        if (!executorService.isTerminated()) {
            console.log("Thread executorService failed to shut down, forcing shutdown!");

            Thread.sleep(TIME_FOR_INSTANT_SHUTDOWN);
            List<Runnable> stillRunning = executorService.shutdownNow();

            console.log("Successfully shut down thread executorService when " + stillRunning.size() + " threads were still running");
        }
    }

    public void setOnClientConnect(Action<Client> onClientConnect) {
        this.onClientConnect = onClientConnect;
    }

    public void setOnClientDisconnect(Action<Client> onClientDisconnect) {
        this.onClientDisconnect = onClientDisconnect;
    }

    public Console getConsole() {
        return console;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    private class ClientListener implements Runnable {
        @Override
        public void run() {
            console.log("Listening for clients");

            while (!Thread.currentThread().isInterrupted()) {
                if (!serverSocket.isClosed()) {
                    try {
                        Socket socket = serverSocket.accept();
                        Client client = new Client(socket, executorService);
                        client.listen();

                        Channel changeStream = client.getChannel(AssetChange.class);

                        executorService.submit(new ClientHandler(client));
                        executorService.submit(new ChangeListener(changeStream));
                    } catch (SocketException e) {
                        //probably should handle this
                        break;
                    } catch (IOException e) {
                        console.log(e);
                    }
                } else {
                    break;
                }
            }
        }
    }

    private class ClientHandler implements Runnable {
        private final Client client;

        //consider making a separate object for socket - related things here
        public ClientHandler(Client client) {
            this.client = client;

            //no "this" keyword, would refer to inner class
            clients.add(client);
        }

        @Override
        public void run() {
            try {
                console.log(Level.FINE, "Client connected at " + client.getSocket().getInetAddress());

                if (onClientConnect != null) {
                    onClientConnect.act(client);
                }

                while (!client.getSocket().isClosed()) {
                    try {
                        //handle commands here
                        Channel channel = client.getChannel(Command.class);

                        //TODO: implement better format for this action, should method read really need class?
                        Command command = channel.read(Command.class);
                        command.handle(client, Server.this);
                    } catch (SocketException | EOFException e) {
                        try {
                            console.log("Terminating connection to " + client.getSocket().getInetAddress());
                            client.getSocket().close();
                        } catch (IOException e1) {
                            console.log(e1);
                        }
                    } catch (Exception e) {
                        client.getChannel(Exception.class).write(e);
                    }
                }

                console.log(Level.FINE, "Client disconnected at " + client.getSocket().getInetAddress());

                if (onClientDisconnect != null) {
                    onClientDisconnect.act(client);
                }
            } catch (Exception e) {
                console.log(e);
            }
        }
    }

    private class ChangeListener implements Runnable {
        private final Channel changeStream;

        public ChangeListener(Channel changeStream) {
            this.changeStream = changeStream;
        }

        @Override
        public void run() {
            try {
                Object obj = changeStream.read();
                if (AssetChange.class.isAssignableFrom(obj.getClass())) {
                    AssetChange change = AssetChange.class.cast(obj);
                    System.out.println(change);
                } else {
                    console.log("Object of type " + obj.getClass() + " is not valid in asset change stream!");
                }
            } catch (IOException | ClassNotFoundException e) {
                console.log(e);
            }
        }
    }
}