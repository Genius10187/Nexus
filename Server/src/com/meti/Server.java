package com.meti;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/7/2017
 */
public class Server {
    private static final long TIME_FOR_INSTANT_SHUTDOWN = 5000;

    private final ExecutorService service = Executors.newCachedThreadPool();
    private final ServerSocket serverSocket;
    private final Console console;

    /*
    maxQueueSize and backlog are the same thing
    it specifies how many sockets can connect to
    the server at once, i.e. user limit
    */
    public Server(int port, int maxQueueSize, InetAddress address, Console console) throws IOException {
        this.serverSocket = new ServerSocket(port, maxQueueSize, address);
        this.console = console;
    }

    public Server(int port, int maxQueueSize, Console console) throws IOException {
        this.serverSocket = new ServerSocket(port, maxQueueSize);
        this.console = console;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void host() {
        service.submit(new ClientListener());
    }

    public void stop() throws IOException, InterruptedException {
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
            console.log("Located client at " + client.getSocket().getInetAddress());

            while (!client.getSocket().isClosed()) {
                try {
                    //should we assume this is an instance of Command?
                    Command command = client.read();
                    client.run(command);
                } catch (IOException | ClassNotFoundException e) {
                    console.log(e);
                }
            }
        }
    }
}