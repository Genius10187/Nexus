package com.meti.server.asset.text;

import com.meti.server.asset.Asset;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TextAsset extends Asset<String> {
    public TextAsset(File file, String... lines) {
        super(file);

        String content = Arrays.stream(lines).collect(Collectors.joining());

        setContent(content);
    }

    //list interface not serializable
    public TextAsset(File file, ArrayList<String> lines) {
        super(file);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lines.size() - 2; i++) {
            builder.append(lines.get(i) + "\n");
        }
        builder.append(lines.get(lines.size() - 1));
        setContent(builder.toString());
    }
}
