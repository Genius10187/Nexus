package com.meti.app;

import com.meti.io.connect.ConnectionHandler;
import com.meti.io.connect.connections.Connection;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/15/2018
 */
public class ConnectionContext {
    private final ConnectionHandler handler = new ConnectionHandler() {
        @Override
        public Object handleThrows(Connection connection) {
            return null;
        }
    };

    public ConnectionContext() {
    }
}
