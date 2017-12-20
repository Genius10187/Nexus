package com.meti.lib.io.server;

import com.meti.lib.io.client.ClientLoop;
import com.meti.lib.io.server.asset.AssetManager;
import com.meti.lib.util.Console;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class ServerState {
    private final ExecutorService service = Executors.newCachedThreadPool();
    private final List<ClientLoop> clientLoops = new ArrayList<>();
    private final AssetManager manager = new AssetManager();
    private final ServerSocket serverSocket;
    private final Console console;

    public ServerState(ServerSocket serverSocket, Console console) {
        this.serverSocket = serverSocket;
        this.console = console;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public ExecutorService getService() {
        return service;
    }

    public AssetManager getManager() {
        return manager;
    }

    public Console getConsole() {
        return console;
    }

    public List<ClientLoop> getClientLoops() {
        return clientLoops;
    }
}
