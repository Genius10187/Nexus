package com.meti.asset;

import java.io.File;
import java.io.IOException;

public interface AssetBuilder {
    Asset build(File file) throws IOException;
}
