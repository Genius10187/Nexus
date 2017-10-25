package com.meti.server.asset;

import java.io.Serializable;

public abstract class AssetChange implements Serializable {
    private final String assetPath;

    public AssetChange(String assetPath) {
        this.assetPath = assetPath;
    }

    public String getAssetPath() {
        return assetPath;
    }

    public abstract void update(Asset<?> asset);
}
