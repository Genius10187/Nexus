package com.meti.server.asset.text;

import com.meti.server.asset.Asset;
import com.meti.server.asset.AssetBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TextBuilder implements AssetBuilder<String> {

    @Override
    public Asset<String> build(File file) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }

        return new TextAsset(file, lines);
    }

    @Override
    public String[] getExtensions() {
        return new String[]{
                "txt"
        };
    }
}
