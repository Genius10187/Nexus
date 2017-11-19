package com.meti.io.command;

import com.meti.Server;
import com.meti.io.Client;

public class StringCommand extends Command {

    public StringCommand(String name, String... args) {
    }

    @Override
    public void handle(Client client, Server server) {
        //TODO: handle string command
    }
}
