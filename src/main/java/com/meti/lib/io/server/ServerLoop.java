package com.meti.lib.io.server;

import com.meti.lib.io.client.Client;
import com.meti.lib.io.server.command.Command;
import com.meti.lib.util.Loop;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class ServerLoop extends Loop {
    private final Client client;
    private final ServerState state;

    public ServerLoop(Client client, ServerState state) {
        this.client = client;
        this.state = state;
    }

    @Override
    public void loop() throws IOException {
        try {
            if (client.hasSuperClass(Command.class)) {
                Command command = client.readSuperClass(Command.class);
                command.perform(state, client);
            } else if (client.hasSuperClass(Change.class)) {
                Change change = client.readSuperClass(Change.class);
                change.update(state);

                for (ServerLoop loop : state.getServerLoops()) {
                    loop.getClient().write(change);
                    loop.getClient().flush();
                }
            } else if (client.hasSuperClass(Object.class)) {
                //TODO: object type
            }
        } catch (Exception e) {
            client.write(e);
            client.flush();
        }
    }

    public Client getClient() {
        return client;
    }
}
