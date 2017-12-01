package com.meti.util;

import com.meti.server.ServerMain;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/30/2017
 */
class ServerMainTest {
    @Test
    void createSocketNumberFormatException() {
        assertThrows(NumberFormatException.class, () -> ServerMain.createSocket("port"));
    }

    @Test
    void createSocketIllegalArgumentException() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> ServerMain.createSocket("-1")),
                () -> assertThrows(IllegalArgumentException.class, () -> ServerMain.createSocket("65566")));
    }
}