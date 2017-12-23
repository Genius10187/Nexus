package com.meti.lib.io.client;

import java.util.Properties;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/22/2017
 */
public class ClientState {
    private final Client client;
    private final Properties properties = new Properties();

    {
        properties.setProperty("username", "Anonymous");
    }

    public ClientState(Client client) {
        this.client = client;
    }

    public Properties getProperties() {
        return properties;
    }

    public Client getClient() {
        return client;
    }
}
