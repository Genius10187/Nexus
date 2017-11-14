package com.meti.io;

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
public class Command implements Serializable {
    private final String name;
    private final String[] params;

    /**
     * Creates a new Command with a specified name and parameters.
     *
     * @param name   The name
     * @param params The parameters
     */
    public Command(String name, String... params) {
        this.name = name;
        this.params = params;
    }

    /**
     * Gets the name
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the parameters
     *
     * @return The parameters
     */
    public String[] getParams() {
        return params;
    }
}
