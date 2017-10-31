package com.meti.server;

import com.meti.server.asset.Asset;
import com.meti.server.util.Cargo;
import com.meti.server.util.Command;
import com.meti.util.Utility;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
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
        if ("login".equals(next.getName())) {
            String password = (String) next.getArgs()[0];
            if (password.equals(server.getPassword())) {
                getInstance().log(Level.INFO, "Client " + socket.getInetAddress() + " has connected with valid password");
            } else {
                getInstance().log(Level.INFO, "Client has invalid password, kicking out!");
                socket.close();
            }
        } else if ("disconnect".equals(next.getName())) {
            socket.close();
        } else if ("list".equals(next.getName())) {
            Cargo<String> cargo = new Cargo<>();
            HashMap<String, Asset<?>> assets = server.getAssetManager().getAssets();
            ArrayList<String> paths = new ArrayList<>();
            paths.addAll(assets.keySet());
            cargo.getContents().addAll(paths);

            sendable.send(cargo, true);

        } else if ("get".equals(next.getName())) {//should be declared other side...
            String path = Utility.castIfOfInstance(next.getArgs()[0], String.class);
            Asset<?> asset = server.getAssetManager().getAsset(path);

            sendable.send(asset, true);
        }
    }
}
