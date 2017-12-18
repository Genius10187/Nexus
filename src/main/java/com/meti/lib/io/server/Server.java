package com.meti.lib.io.server;

import com.meti.lib.io.client.Client;
import com.meti.lib.io.client.ClientLoop;
import com.meti.lib.io.client.Clients;
import com.meti.lib.io.source.ObjectSource;
import com.meti.lib.io.source.Sources;
import com.meti.lib.util.Loop;
import com.meti.lib.util.exec.Executable;
import com.meti.lib.util.exec.Executables;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class Server implements Executable {
    private final ServerState state;

    public Server(ServerSocket serverSocket) {
        this.state = new ServerState(serverSocket);
    }

    @Override
    public Callable[] getCallables() {
        return new Callable[0];
    }

    @Override
    public Runnable[] getRunnables() {
        return new Runnable[]{new ListenLoop()};
    }

    private class ListenLoop extends Loop {
        @Override
        public void loop() throws Exception {
            Socket socket = state.getServerSocket().accept();
            ObjectSource objectSource = Sources.createObjectSource(socket);
            Client client = Clients.create(objectSource);
            ClientLoop loop = new ClientLoop(client, state);
            Executable loopExecutable = Executables.fromRunnable(loop);

            Executables.executeAll(state.getService(), loopExecutable, client);
        }
    }
}
