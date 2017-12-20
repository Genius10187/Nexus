package com.meti.app.text;

import com.meti.lib.io.server.asset.Asset;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/20/2017
 */
public class TextAsset extends Asset<Character> {
    public TextAsset(Character[] content) {
        super(content);
    }
}
