package com.meti.io.command;

import com.meti.Server;
import com.meti.io.Client;

import java.io.IOException;

public class ListCommand extends Command {
    //TODO: consider TypeCommand superclass
    private final int type;

    public ListCommand(int type) {
        this.type = type;
    }

    @Override
    public void handle(Client client, Server server) throws IOException {

    }
}
