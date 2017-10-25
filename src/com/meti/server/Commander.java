package com.meti.server;

import com.meti.server.asset.Asset;
import com.meti.server.util.Cargo;
import com.meti.server.util.Command;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;

import static com.meti.Main.getInstance;

public class Commander {
    private Server server;
    private Socket socket;
    private Sendable sendable;

    public Commander() {
    }

    public Commander(Server server, Socket socket, Sendable sendable) {
        this.server = server;
        this.socket = socket;
        this.sendable = sendable;
    }

    public void runCommand(Command next) throws IOException {
        switch (next.getName()) {
            case "login":
                String password = (String) next.getArgs()[0];
                if (password.equals(server.getPassword())) {
                    getInstance().log(Level.INFO, "Client " + socket.getInetAddress() + " has connected with valid password");
                } else {
                    getInstance().log(Level.INFO, "Client has invalid password, kicking out!");
                    socket.close();
                }
                break;
            case "disconnect":
                socket.close();
                break;
            case "list":
                Cargo cargo = new Cargo<>();
                ArrayList<Asset<?>> assets = server.getAssetManager().getAssets();
                ArrayList<String> paths = new ArrayList<>();
                assets.forEach(asset -> paths.add(asset.getPath()));
                cargo.getContents().addAll(paths);

                sendable.send(cargo, true);
                break;
        }
    }
}
