package com.meti.asset;

import com.meti.util.Console;
import com.meti.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;

public class AssetManager {
    private final HashMap<String, AssetBuilder> builderMap = new HashMap<>();
    private final HashMap<File, Asset> assetMap = new HashMap<>();
    private final Console console;

    {
        //TODO: build AssetBuilders
    }

    public AssetManager(Console console) throws MalformedURLException, ClassNotFoundException {
        this.console = console;
    }

    public void read(File directory) {
        List<File> files = Utility.search(directory);
        files.forEach(file -> {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
                assetMap.put(file, builderMap.get(Utility.getExtension(file)).build(inputStream));
            } catch (IOException e) {
                console.log(e);
            }
        });
    }
}
