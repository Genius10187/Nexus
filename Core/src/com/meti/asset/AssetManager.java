package com.meti.asset;

import com.meti.util.Console;
import com.meti.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class AssetManager {
    private final HashMap<String, AssetBuilder> builderMap = new HashMap<>();
    private final HashMap<File, Asset> assetMap = new HashMap<>();
    private final Console console;

    {
        //TODO: build AssetBuilders
    }

    public AssetManager(Console console) {
        this.console = console;
    }

    public void read(File directory) {
        List<File> files = Utility.search(directory);
        files.forEach(file -> {
            try {
                InputStream inputStream = new FileInputStream(file);
                String extension = Utility.getExtension(file);
                AssetBuilder assetBuilder = builderMap.get(extension);

                //might be a directory
                if (extension != null && assetBuilder != null) {
                    assetMap.put(file, assetBuilder.build(inputStream));
                }
            } catch (IOException e) {
                console.log(e);
            }
        });
    }

    public Set<File> getFiles() {
        return assetMap.keySet();
    }
}
