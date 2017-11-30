package com.meti.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/30/2017
 */
public class ServerMain {
    private static final Logger logger = Logger.getLogger("Application");
    private static boolean running = false;

    public static void main(String[] args) {
        init();
        run();
    }

    private static void init() {
        logger.log(Level.INFO, "Initializing server");

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter in a port, or 0 for Java to generate one:");

            ServerSocket serverSocket = createSocket(scanner.nextLine());
            serverSocket.close();
        } catch (IOException e) {
            logger.log(Level.INFO, "Error in initializing server");
        }
    }

    public static ServerSocket createSocket(String input) throws IOException {
        int port = Integer.parseInt(input);
        return new ServerSocket(port);
    }

    private static void run() {
        logger.log(Level.INFO, "Running server");

        running = true;
        while (running) {

        }
    }
}
