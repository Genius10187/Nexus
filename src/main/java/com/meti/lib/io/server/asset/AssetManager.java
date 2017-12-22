package com.meti.lib.io.server.asset;

import com.meti.lib.io.server.asset.Array.ArrayBuilder;
import com.meti.lib.io.source.Sources;
import com.meti.lib.util.Utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class AssetManager {
    private final Random random = new Random();

    private final HashMap<String, AssetBuilder<?, InputStream, OutputStream>> builderHashMap = new HashMap<>();
    private final HashMap<Long, Asset> assetHashMap = new HashMap<>();

    public void load(File file) throws IOException {
        List<File> fileList = Utility.scan(file);
        for (File location : fileList) {
            if (!location.isDirectory()) {
                String ext = Utility.getExtension(location);
                AssetBuilder<?, InputStream, OutputStream> builder = builderHashMap.get(ext);
                if (builder != null) {
                    builder = new ArrayBuilder();

                    Asset asset = builder.build(location, Sources.createBasicSource(location));
                    assetHashMap.put(random.nextLong(), asset);
                }
            } else {
                //TODO: handle directories

            }
        }
    }

    public Asset getAsset(long assetID) {
        return assetHashMap.get(assetID);
    }

    public List<File> getFiles() {
        List<File> paths = new ArrayList<>();
        assetHashMap.values().forEach(new Consumer<Asset>() {
            @Override
            public void accept(Asset asset) {
                paths.add(asset.getLocation());
            }
        });

        return paths;
    }
}
