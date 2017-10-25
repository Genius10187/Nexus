package com.meti.server.asset.text;

import com.meti.server.asset.Asset;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class TextAsset extends Asset<String> {
    private final ArrayList<String> lines = new ArrayList<>();

    public TextAsset(File file, String... args) {
        super(file);
        this.lines.addAll(Arrays.asList(args));
    }

    //list interface not serializable
    public TextAsset(File file, ArrayList<String> lines) {
        super(file);
        this.lines.addAll(lines);
    }

    public ArrayList<String> getLines() {
        return lines;
    }
}
