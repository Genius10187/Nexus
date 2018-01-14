package com.meti;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/30/2017
 */
public class AppState extends State {
    private final Console console = new Console("Nexus");

    public Console getConsole() {
        return console;
    }
}
