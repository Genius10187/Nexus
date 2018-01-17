package com.meti.app;

import com.meti.io.Peer;
import com.meti.io.connect.ConnectionHandler;
import com.meti.io.connect.connections.Connection;

import java.util.concurrent.ExecutorService;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/15/2018
 */
public class ConnectionContext {
    private final Peer peer;

    public ConnectionContext(ExecutorService service) {
        peer = new Peer(new NexusConnectionHandler(), service);
    }

    public Peer getPeer() {
        return peer;
    }

    private class NexusConnectionHandler extends ConnectionHandler {
        @Override
        public Object handleThrows(Connection connection) {
            //TODO: create implementation for NexusConnectionHandler
            return null;
        }
    }
}
