package com.meti.command;

import com.meti.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public interface Command extends Serializable {
    void perform(Server server, ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException;
}
