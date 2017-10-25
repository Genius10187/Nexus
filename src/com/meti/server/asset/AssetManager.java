package com.meti.server.asset;

import com.meti.server.asset.text.TextBuilder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.meti.util.Utility.*;

public class AssetManager {
    private final Class[] builderClasses = {
            TextBuilder.class
    };

    private final HashMap<String, AssetBuilder<?>> builders = new HashMap<>();
    private final HashMap<String, Asset<?>> assets = new HashMap<>();

    public void load() throws IllegalAccessException, InstantiationException {
        for (Class c : builderClasses) {
            AssetBuilder<?> builder = castIfOfInstance(c.newInstance(), AssetBuilder.class);
            for (String ext : builder.getExtensions()) {
                builders.put(ext, builder);
            }
        }
    }

    public void read(File directory) throws IOException {
        throwIfNotLoaded();

        createIfNotExists(directory, true);
        List<File> files = getFiles(directory);

        for (File file : files) {
            assets.put(file.getPath(), builders.get(getExtension(file)).build(file));
        }
    }

    public void throwIfNotLoaded() {
        if (builders.size() == 0) {
            throw new RuntimeException("AssetManager not loaded!");
        }
    }

    public HashMap<String, Asset<?>> getAssets() {
        return assets;
    }

    public Asset<?> getAsset(String path) {
        return assets.get(path);
    }
}
