package com.meti.lib.io.client;

import com.meti.lib.io.server.ServerState;
import com.meti.lib.util.Loop;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class ClientLoop extends Loop {
    private final Client client;
    private final ServerState state;

    public ClientLoop(Client client, ServerState state) {
        this.client = client;
        this.state = state;
    }

    @Override
    public void loop() {
    }
}
