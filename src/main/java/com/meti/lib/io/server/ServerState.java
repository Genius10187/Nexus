package com.meti.lib.io.server;

import com.meti.lib.io.server.asset.AssetManager;
import com.meti.lib.util.Console;
import com.meti.lib.util.Handler;

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
    private final List<ServerLoop> serverLoops = new ArrayList<>();
    private final AssetManager manager = new AssetManager();
    private final ChatState chatState = new ChatState();
    private final ServerSocket serverSocket;
    private final Console console;
    private Handler<ServerLoop> onAddServerLoop;

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

    public ChatState getChatState() {
        return chatState;
    }

    public Console getConsole() {
        return console;
    }

    public List<ServerLoop> getServerLoops() {
        return serverLoops;
    }

    public Handler<ServerLoop> getOnAddServerLoop() {
        return onAddServerLoop;
    }

    public void setOnAddServerLoop(Handler<ServerLoop> onAddServerLoop) {
        this.onAddServerLoop = onAddServerLoop;
    }

    public void addServerLoop(ServerLoop loop) {
        serverLoops.add(loop);
        onAddServerLoop.act(loop);
    }
}
