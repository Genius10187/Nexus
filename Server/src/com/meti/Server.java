package com.meti;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/7/2017
 */
public class Server {
    private final ServerSocket serverSocket;

    /*
    maxQueueSize and backlog are the same thing
    it specifies how many sockets can connect to
    the server at once, i.e. user limit
    */
    public Server(int port, int maxQueueSize, InetAddress address) throws IOException {
        this.serverSocket = new ServerSocket(port, maxQueueSize, address);
    }

    public Server(int port, int maxQueueSize) throws IOException {
        this.serverSocket = new ServerSocket(port, maxQueueSize);
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void host() {
        //TODO: host method
    }
}
