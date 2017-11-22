package com.meti.io.channel.out;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/21/2017
 */
public interface OutputChannel {
    void write(Serializable serializable) throws IOException;

    void writeAll(Serializable... serializables) throws IOException;
}
