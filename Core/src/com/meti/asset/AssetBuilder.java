package com.meti.asset;

import java.io.File;
import java.io.IOException;

public interface AssetBuilder {
    String[] getExtensions();

    Asset build(File inputStream) throws IOException;
}
