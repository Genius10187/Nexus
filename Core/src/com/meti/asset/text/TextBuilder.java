package com.meti.asset.text;

import com.meti.asset.Asset;
import com.meti.asset.AssetBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/11/2017
 */
public class TextBuilder implements AssetBuilder {
    @Override
    public String[] getExtensions() {
        return new String[]{
                "txt"
        };
    }

    @Override
    public Asset build(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<String> lines = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }

        Asset asset = new TextAsset(file);
        asset.setContent(lines);
        return asset;
    }
}
