package com.meti.app;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/15/2018
 */
public class AppState {
    private final ConnectionContext context = new ConnectionContext();

    public ConnectionContext getContext() {
        return context;
    }
}
