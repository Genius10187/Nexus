package com.meti.server;

import java.util.Scanner;

import static java.lang.System.out;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/30/2017
 */
public class ServerMain {
    public static void main(String[] args) {
        Server server = new Server();

        Scanner scanner = new Scanner(System.in);
        out.println("Enter in a port, or 0 for Java to generate one:");

        server.init(scanner.nextLine());
        server.run();
    }
}
