package com.meti.command;

import com.meti.server.AssetManager;
import com.meti.server.Server;
import com.meti.util.Cargo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ListCommand implements Command {
    public static final int TYPE_FILES = 0;
    public static final int TYPE_EXTENSIONS = 1;

    private final int type;

    public ListCommand(int type) {
        this.type = type;
    }

    @Override
    public void perform(Server server, ObjectInputStream inputStream, ObjectOutputStream outputStream) throws IOException {
        switch (type) {
            case TYPE_FILES: {
                Cargo cargo = new Cargo(AssetManager.getFiles());

                outputStream.writeObject(cargo);
                break;
            }
            case TYPE_EXTENSIONS: {
                Cargo cargo = new Cargo(AssetManager.getExtensions());

                outputStream.writeObject(cargo);
                break;
            }
            default:
                outputStream.writeObject(new IllegalArgumentException("Argument of type " + type + " does not exist"));

                break;
        }

        outputStream.flush();
    }
}
