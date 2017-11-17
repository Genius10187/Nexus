package com.meti.io.command;

import com.meti.Server;
import com.meti.io.Client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListCommand extends Command {
    public static final int TYPE_PATHS = 0;
    //TODO: consider TypeCommand superclass
    private final int type;

    public ListCommand(int type) {
        this.type = type;
    }

    @Override
    public void handle(Client client, Server server) throws IOException {
        switch (type) {
            case TYPE_PATHS:
                List<String> paths = new ArrayList<>();
                Set<File> files = server.getAssetManager().getFiles();

                for (File file : files) {
                    paths.add(file.getPath());
                }

                client.writeAll(paths);
                break;
            default:
                client.write(new IllegalArgumentException("Type " + type + " does not exist "));
                break;
        }
    }
}
