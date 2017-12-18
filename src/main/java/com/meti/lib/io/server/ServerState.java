package com.meti.lib.io.server;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class ServerState {
    private final ExecutorService service = Executors.newCachedThreadPool();
    private final ServerSocket serverSocket;

    public ServerState(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public ExecutorService getService() {
        return service;
    }
}
