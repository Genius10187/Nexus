package com.meti.lib.io.server;

import com.meti.lib.io.client.Client;
import com.meti.lib.io.client.ClientLoop;
import com.meti.lib.io.client.Clients;
import com.meti.lib.io.source.ObjectSource;
import com.meti.lib.io.source.Sources;
import com.meti.lib.util.Console;
import com.meti.lib.util.Loop;
import com.meti.lib.util.execute.Executable;
import com.meti.lib.util.execute.Executables;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class Server implements Executable {
    private final ServerState state;

    public Server(ServerSocket serverSocket, Console console) {
        this.state = new ServerState(serverSocket, console);
    }

    @Override
    public Callable[] getCallables() {
        return new Callable[0];
    }

    @Override
    public Runnable[] getRunnables() {
        return new Runnable[]{new ListenLoop()};
    }

    public ServerState getState() {
        return state;
    }

    public int getPort() {
        return state.getServerSocket().getLocalPort();
    }

    private class ListenLoop extends Loop {
        @Override
        public void loop() throws Exception {
            Socket socket = state.getServerSocket().accept();
            state.getConsole().log(Level.FINE, "Located client at " + socket.getInetAddress());

            ObjectSource objectSource = Sources.createObjectSource(socket);

            state.getConsole().log(Level.FINE, "Created client object for " + socket.getInetAddress());
            Client client = Clients.create(objectSource);
            ClientLoop loop = new ClientLoop(client, state);
            state.getClientLoops().add(loop);

            Executable loopExecutable = Executables.fromRunnable(loop);
            Executables.executeAll(state.getService(), loopExecutable, client);
        }
    }
}
