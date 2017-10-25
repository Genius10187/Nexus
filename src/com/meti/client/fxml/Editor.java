package com.meti.client.fxml;

import com.meti.client.Client;
import com.meti.server.asset.Asset;

public abstract class Editor {
    protected Client client;

    public abstract String[] getExtensions();

    public abstract void load(Asset<?> asset, Client clientDisplay);

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
