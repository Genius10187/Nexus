package com.meti.io.command;

import com.meti.Server;
import com.meti.asset.AssetManager;
import com.meti.io.Client;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class GetCommand extends Command {
    public static final int TYPE_PATH = 1;
    public static final int TYPE_SIZE = 2;
    public static final int TYPE_VALUE = 0;
    private static final int TYPE_SUPPORTED = 3;

    private final int type;
    private final File file;

    public GetCommand(File file, int type) {
        this.file = file;
        this.type = type;
    }

    @Override
    public void handle(Client client, Server server) throws IOException {
        AssetManager manager = server.getAssetManager();

        Serializable obj;

        switch (type) {
            case TYPE_VALUE:
                obj = manager.getValue(file);
                break;
            case TYPE_PATH:
                obj = file.getPath();
                break;
            case TYPE_SIZE:
                obj = manager.getSize(file);
                break;
            case TYPE_SUPPORTED:
                obj = manager.isSupported(file);
                break;
            default:
                obj = new IllegalArgumentException("Type " + type + " does not exist");
                break;
        }

        client.getChannel(obj.getClass()).writeAll(obj);
    }
}
