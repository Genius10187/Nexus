package com.meti.io.channel.in;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/21/2017
 */
public interface InputChannel {
    <T> T read(Class<T> c) throws IOException, ClassNotFoundException;

    Serializable read() throws IOException, ClassNotFoundException;

    Serializable[] readAll() throws IOException, ClassNotFoundException;
}
