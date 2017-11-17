package com.meti.io.command;

import com.meti.Server;
import com.meti.io.Client;

import java.io.IOException;

public class StringCommand extends Command {
    private final String name;
    private final String[] args;

    public StringCommand(String name, String... args) {
        this.name = name;
        this.args = args;
    }

    @Override
    public void handle(Client client, Server server) throws IOException {
        //TODO: handle stringCommmand
    }
}
