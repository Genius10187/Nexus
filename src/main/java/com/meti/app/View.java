package com.meti.app;

import com.meti.lib.io.client.ClientState;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/22/2017
 */
public interface View {
    //TODO: convert to class
    void setClientState(ClientState state);

    void init() throws IOException;
}
