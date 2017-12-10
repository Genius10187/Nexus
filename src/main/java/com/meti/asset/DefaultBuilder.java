package com.meti.asset;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DefaultBuilder implements AssetBuilder {
    @Override
    public Asset build(File file) throws IOException {
        FileReader reader = new FileReader(file);

        //might be a bit memory intensive
        ArrayList<Integer> integerList = new ArrayList<>();
        int b;
        while ((b = reader.read()) != -1) {
            integerList.add(b);
        }

        int[] array = new int[integerList.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = integerList.get(i);
        }

        return new ByteAsset(array);
    }
}
