package com.meti.server.asset.text;

import com.meti.server.asset.Asset;
import com.meti.server.asset.AssetChange;

import java.util.ArrayList;

import static com.meti.util.Utility.castIfOfInstance;

public class TextChange extends AssetChange {
    public static final int CHAR_ADDED = 0;
    public static final int CHAR_REMOVED = 1;

    private String character;
    private int type;

    public TextChange(String assetPath) {
        super(assetPath);
    }

    @Override
    public void update(Asset<?> asset) {
        ArrayList<?> content = castIfOfInstance(asset.getContent(), ArrayList.class);

        String line = castIfOfInstance(content.get(content.size() - 1), String.class);
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setType(int type) {
        this.type = type;
    }
}
