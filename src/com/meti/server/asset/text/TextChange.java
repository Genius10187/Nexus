package com.meti.server.asset.text;

import com.meti.server.asset.Asset;
import com.meti.server.asset.AssetChange;

import java.io.Serializable;

public class TextChange extends AssetChange {


    private String value;

    public TextChange(String assetPath) {
        super(assetPath);
    }

    @Override
    public <T extends Serializable> void update(Asset<T> asset) {
        //String content = Utility.castIfOfInstance(asset.getContent(), String.class);

        //hmmm
        asset.setContent((T) value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
