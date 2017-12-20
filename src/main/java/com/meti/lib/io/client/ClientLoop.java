package com.meti.lib.io.client;

import com.meti.lib.io.server.ServerState;
import com.meti.lib.io.server.asset.AssetChange;
import com.meti.lib.io.server.command.Command;
import com.meti.lib.util.Loop;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class ClientLoop extends Loop {
    private final Client client;
    private final ServerState state;

    public ClientLoop(Client client, ServerState state) {
        this.client = client;
        this.state = state;
    }

    @Override
    public void loop() throws IOException {
        try {
            if (client.hasSuperClass(Command.class)) {
                Command command = client.readSuperClass(Command.class);
                command.perform(state, client);
            } else if (client.hasSuperClass(AssetChange.class)) {

            } else if (client.hasSuperClass(Object.class)) {

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
