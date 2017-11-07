package com.meti;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
        service.shutdown();
        serverSocket.close();

        if (!service.isTerminated()) {
            Thread.sleep(TIME_FOR_INSTANT_SHUTDOWN);
            service.shutdownNow();
        }
    }

    private class ClientListener implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Socket socket = serverSocket.accept();
                    service.submit(new ClientHandler(socket));
                } catch (IOException e) {
                    console.log(e);
                }
            }
        }
    }

    private class ClientHandler implements Runnable {
        private final Socket socket;

        //consider making a separate object for socket - related things here
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            //TODO: handle commands here
        }
    }
}