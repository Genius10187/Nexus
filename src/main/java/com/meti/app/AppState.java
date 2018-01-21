package com.meti.app;

import com.meti.util.console.Console;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/15/2018
 */
public class AppState {
    private final ExecutorService service = Executors.newCachedThreadPool();
    private final Console console = new Console();
    private final ConnectionContext context;

    public AppState() {
        context = new ConnectionContext(service);
    }

    public ConnectionContext getContext() {
        return context;
    }

    public Console getConsole() {
        return console;
    }

    public ExecutorService getService() {
        return service;
    }
}
