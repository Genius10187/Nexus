package com.meti.server.asset.text;

import com.meti.server.asset.Asset;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class TextAsset extends Asset<ArrayList<String>> {
    public TextAsset(File file, String... args) {
        super(file);

        setContent(new ArrayList<>());
        this.content.addAll(Arrays.asList(args));
    }

    //list interface not serializable
    public TextAsset(File file, ArrayList<String> lines) {
        super(file);

        setContent(new ArrayList<>());
        this.content.addAll(lines);
    }

    public ArrayList<String> getLines() {
        return content;
    }
}
