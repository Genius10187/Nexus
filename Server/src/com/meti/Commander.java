package com.meti;

import com.meti.io.Client;
import com.meti.io.Command;

import java.io.IOException;

public interface Commander {
    //i have no idea what I'm doing here
    void run(Command command, Client client) throws IOException;
}