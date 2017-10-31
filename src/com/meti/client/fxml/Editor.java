package com.meti.client.fxml;

import com.meti.client.Client;
import com.meti.server.asset.Asset;
import com.meti.server.asset.AssetChange;

public abstract class Editor {
    protected Client client;
    protected Asset<?> asset;

    private AssetChange[] changes;

    public Asset<?> getAsset() {
        return asset;
    }

    public void setAsset(Asset<?> asset) {
        this.asset = asset;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public abstract void update(AssetChange change);

    public abstract Class[] getAssetChangeClasses();

    public abstract String[] getExtensions();

    public abstract void load(Asset<?> asset, Client clientDisplay);
}
