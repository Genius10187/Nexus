package com.meti.asset;

import java.io.InputStream;

public interface AssetBuilder {
    Asset build(InputStream inputStream);
}
