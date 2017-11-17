package com.meti.io.command;

import com.meti.Server;
import com.meti.io.Client;

import java.io.IOException;
import java.io.Serializable;

/**
 * <p>
 * Specifies an object which is sent from a client to a server.
 * A command details how a server should respond.
 * This may be listing a type of data, getting a value of an Asset,
 * or preparing the server to anticipate the next sent object.
 * </p>
 *
 * @see com.meti.asset.Asset
 */
public abstract class Command implements Serializable {
    protected Command() {
    }

    public abstract void handle(Client client, Server server) throws IOException;
}
