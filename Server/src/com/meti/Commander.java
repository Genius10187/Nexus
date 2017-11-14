package com.meti;

import com.meti.io.Client;
import com.meti.io.Command;

import java.io.IOException;

public interface Commander {
    void run(Command command, Client client) throws IOException;
}