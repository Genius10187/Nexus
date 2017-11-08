package com.meti.asset;

import java.io.ObjectInputStream;

public interface AssetBuilder {
    //not sure if this should be the type of stream to use
    Asset build(ObjectInputStream inputStream);
}
