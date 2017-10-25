package com.meti.server.asset;

import com.meti.server.asset.text.TextBuilder;
import com.meti.util.Utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AssetManager {
    private final Class[] builderClasses = {
            TextBuilder.class
    };

    private final HashMap<String, AssetBuilder<?>> builders = new HashMap<>();
    private final ArrayList<Asset<?>> assets = new ArrayList<>();

    public void load() throws IllegalAccessException, InstantiationException {
        for (Class c : builderClasses) {
            AssetBuilder<?> builder = Utility.castIfOfInstance(c.newInstance(), AssetBuilder.class);
            for (String ext : builder.getExtensions()) {
                builders.put(ext, builder);
            }
        }
    }

    public void read(File directory) throws IOException {
        throwIfNotLoaded();

        Utility.createIfNotExists(directory, true);
        List<File> files = Utility.getFiles(directory);

        for (File file : files) {
            assets.add(builders.get(Utility.getExtension(file)).build(file));
        }
    }

    public void throwIfNotLoaded() {
        if (builders.size() == 0) {
            throw new RuntimeException("AssetManager not loaded!");
        }
    }
}
