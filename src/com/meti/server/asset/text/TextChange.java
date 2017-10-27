package com.meti.server.asset.text;

import com.meti.server.asset.Asset;
import com.meti.server.asset.AssetChange;
import com.meti.util.Utility;

import java.io.Serializable;

public class TextChange extends AssetChange {
    public static final int CHAR_ADDED = 0;
    public static final int CHAR_REMOVED = 1;

    private String character;
    private int type;

    public TextChange(String assetPath) {
        super(assetPath);
    }

    @Override
    public <T extends Serializable> void update(Asset<T> asset) {
        String content = Utility.castIfOfInstance(asset.getContent(), String.class);

        if (type == CHAR_ADDED) {
            content = content + character;
        } else if (type == CHAR_REMOVED) {
            content = content.substring(0, content.length() - 2);
        }

        //...
        asset.setContent((T) content);
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setType(int type) {
        this.type = type;
    }
}
