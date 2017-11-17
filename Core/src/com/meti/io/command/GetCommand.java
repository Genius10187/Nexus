package com.meti.io.command;

import com.meti.Server;
import com.meti.asset.AssetManager;
import com.meti.io.Client;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class GetCommand extends Command {
    public static final int GET_VALUE = 0;
    public static final int GET_SIZE = 1;
    public static final int GET_SUPPORTED = 2;

    private final int type;
    private final File file;

    public GetCommand(File file, int type) {
        this.file = file;
        this.type = type;
    }

    @Override
    public void handle(Client client, Server server) throws IOException {
        AssetManager manager = server.getAssetManager();

        Serializable obj = null;
        switch (type) {
            case GET_VALUE:
                obj = manager.getValue(file);
                break;
            case GET_SIZE:
                obj = file.length();
                break;
            case GET_SUPPORTED:
                obj = manager.isSupported(file);
                break;
        }

        client.write(obj);
    }
}
