package com.meti.server.asset;

import java.io.Serializable;

public abstract class AssetChange implements Serializable {
    private final Class<? extends Asset> assetClass;
    private final String assetPath;

    public AssetChange(Class<? extends Asset> assetClass, String assetPath) {
        this.assetClass = assetClass;
        this.assetPath = assetPath;
    }

    public String getAssetPath() {
        return assetPath;
    }

    public abstract <T extends Serializable> void update(Asset<T> asset);
}
