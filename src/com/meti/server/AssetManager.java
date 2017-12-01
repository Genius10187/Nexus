package com.meti.server;

import com.meti.asset.Asset;
import com.meti.asset.AssetBuilder;
import com.meti.asset.DefaultBuilder;
import com.meti.util.Utility;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AssetManager {
    private static final HashMap<String, AssetBuilder> builderHashMap = new HashMap<>();
    private static final HashMap<File, Asset> assetHashMap = new HashMap<>();

    private AssetManager() {
    }

    public static List<File> load(File directory) throws IOException {
        List<File> files = Utility.search(directory);

        for (File file : files) {
            AssetBuilder builder = null;
            String extension = Utility.getExtension(file);
            if (builderHashMap.containsKey(extension)) {
                builder = builderHashMap.get(extension);
            } else {
                builder = new DefaultBuilder();
            }

            assetHashMap.put(file, builder.build(file));
        }

        return files;
    }
}
