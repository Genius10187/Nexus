package com.meti.server.asset;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public interface AssetBuilder<T extends Serializable> {
    Asset<T> build(File file) throws IOException;

    String[] getExtensions();
}
