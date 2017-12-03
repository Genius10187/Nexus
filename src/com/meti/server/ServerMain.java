package com.meti.server;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/30/2017
 */
public class ServerMain {
    public static void main(String[] args) {
        Server server = new Server();
        server.init();
        server.run();
    }
}
