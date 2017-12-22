package com.meti.lib.io.client;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/22/2017
 */
public class ClientState {
    private final Client client;

    public ClientState(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }
}
