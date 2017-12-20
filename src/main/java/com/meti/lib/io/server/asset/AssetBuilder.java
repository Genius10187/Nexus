package com.meti.lib.io.server.asset;

import com.meti.lib.io.source.Source;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public interface AssetBuilder<T, A extends InputStream, B extends OutputStream> {
    Asset<T> build(Source<A, B> source) throws IOException;
}
