package com.meti.server;

import java.io.IOException;
import java.io.Serializable;

public interface Sendable {
    void send(Serializable serializable, boolean flush) throws IOException;
}
