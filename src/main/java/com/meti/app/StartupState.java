package com.meti.app;

import com.meti.lib.io.server.Server;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/23/2017
 */
public class StartupState {
    private boolean remote;
    private Server server;

    public boolean isRemote() {
        return remote;
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
