package com.meti.app;

import com.meti.lib.io.client.ClientState;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/22/2017
 */
public interface View {
    void setClientState(ClientState state);

    void init();
}
