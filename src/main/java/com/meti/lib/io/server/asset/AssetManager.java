package com.meti.lib.io.server.asset;

import com.meti.lib.io.source.Sources;
import com.meti.lib.util.Utility;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class AssetManager {
    private final Random random = new Random();

    private final HashMap<String, AssetBuilder> builderHashMap = new HashMap<>();
    private final HashMap<Long, Asset> assetHashMap = new HashMap<>();

    public void load(File file) throws IOException {
        List<File> fileList = Utility.scan(file);
        for (File f : fileList) {
            if (!f.isDirectory()) {
                String ext = Utility.getExtension(f);
                AssetBuilder builder = builderHashMap.get(ext);
                if (builder == null) {
                    //TODO: invalid builder
                } else {
                    Asset asset = builder.build(Sources.createBasicSource(f));
                    assetHashMap.put(random.nextLong(), asset);
                }
            } else {
                //TODO: handle directories
            }
        }
    }
}
