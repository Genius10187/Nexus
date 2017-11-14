package com.meti;

import com.meti.asset.AssetManager;
import com.meti.io.Client;
import com.meti.io.Command;
import com.meti.util.Action;
import com.meti.util.Console;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
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

    private final ExecutorService service = Executors.newCachedThreadPool();
    private final AssetManager assetManager;
    private final ServerSocket serverSocket;
    private final Console console;

    private ArrayList<Client> clients = new ArrayList<>();
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
        service.submit(new ClientListener());
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

        console.log("Shutting down thread service");
        service.shutdown();

        console.log("Shutting down server socket");
        serverSocket.close();

        if (!service.isTerminated()) {
            console.log("Thread service failed to shut down, forcing shutdown!");

            Thread.sleep(TIME_FOR_INSTANT_SHUTDOWN);
            List<Runnable> stillRunning = service.shutdownNow();

            console.log("Successfully shut down thread service when " + stillRunning.size() + " threads were still running");
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

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void runCommand(Command command) {
        switch (command.getName().toLowerCase()) {
            case "say":
                String[] params = command.getParams();
                String line = (params.length == 1) ? params[0] : Arrays.toString(params);
                console.log(line);
                break;
            default:
                console.log("Unknown command!");
        }
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
                        service.submit(new ClientHandler(socket));
                    } catch (SocketException e) {
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
        public ClientHandler(Socket socket) throws IOException {
            this.client = new Client(socket);
        }

        @Override
        public void run() {
            console.log(Level.FINE, "Client connected at " + client.getSocket().getInetAddress());

            onClientConnect.act(client);

            while (!client.getSocket().isClosed()) {
                try {
                    //should we assume this is an instance of Command?
                    Command command = client.read(Command.class);
                    client.run(command);
                } catch (SocketException e) {
                    try {
                        console.log("Terminating connection to " + client.getSocket().getInetAddress());
                        client.getSocket().close();
                    } catch (IOException e1) {
                        console.log(e1);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    console.log(e);
                }
            }

            console.log(Level.FINE, "Client disconnected at " + client.getSocket().getInetAddress());

            onClientDisconnect.act(client);
        }
    }
}