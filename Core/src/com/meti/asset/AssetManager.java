package com.meti.asset;

import com.meti.util.Console;
import com.meti.util.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class AssetManager {
    private final HashMap<String, AssetBuilder> builderMap = new HashMap<>();
    private final HashMap<File, Asset> assetMap = new HashMap<>();
    private final Console console;

    {
        List<File> classFiles = Utility.search(new File("Core"), "java");
        for (File classFile : classFiles) {
            Class<?> c = Utility.getClassFromFile(new File("Core\\src"), classFile);
            if (AssetBuilder.class.isAssignableFrom(c)) {
                AssetBuilder instance = (AssetBuilder) c.newInstance();
                String[] extensions = instance.getExtensions();
                for (String ext : extensions) {
                    builderMap.put(ext, instance);
                }
            }
        }
    }

    //this is a lot of exceptions
    public AssetManager(Console console) throws FileNotFoundException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        this.console = console;
    }

    public void read(File directory) {
        List<File> files = Utility.search(directory);
        files.forEach(file -> {
            String extension = Utility.getExtension(file);
            AssetBuilder assetBuilder = builderMap.get(extension);

            //might be a directory
            if (extension != null && assetBuilder != null) {
                try {
                    assetMap.put(file, assetBuilder.build(file));
                } catch (IOException e) {
                    console.log(e);
                }
            }
        });
    }

    public Set<File> getFiles() {
        return assetMap.keySet();
    }
}
