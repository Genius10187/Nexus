package com.meti.app;

import com.meti.lib.io.client.Client;
import com.meti.lib.io.server.command.Command;
import com.meti.lib.io.server.command.LogCommand;

import java.io.IOException;
import java.util.logging.Level;

import static com.meti.app.Main.console;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2017
 */
public class ClientDisplay {
    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    public void init() {
        console.log(Level.FINE, "Initializing controller");

        try {
            Command test = new LogCommand(Level.WARNING, "Hello Server!");
            client.write(test);
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }
}
