package com.meti.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.out;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/30/2017
 */
public class ServerMain {
    public static final int SHUTDOWN_TIME = 5000;

    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private static final Logger logger = Logger.getLogger("Application");
    private static boolean running = false;
    private static ServerSocket serverSocket;
    private static Scanner systemScanner;

    public static void main(String[] args) {
        init();
        run();
    }

    private static void init() {
        logger.log(Level.INFO, "Initializing server");

        try {
            systemScanner = new Scanner(System.in);
            out.println("Enter in a port, or 0 for Java to generate one:");

            serverSocket = createSocket(systemScanner.nextLine());
            out.println("Created server on port " + serverSocket.getLocalPort());

            Callback<Exception> callback = obj -> {
                StringWriter writer = new StringWriter();
                obj.printStackTrace(new PrintWriter(writer));
                logger.log(Level.WARNING, writer.toString());
            };

            SocketListener listener = new SocketListener(callback, serverSocket);
            executorService.submit(listener);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in initializing server");
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
            String line = systemScanner.nextLine();
            String lowerCase = line.toLowerCase();

            if (lowerCase.contains("stop")) {
                running = false;
            }
        }

        stop();
    }

    private static void stop() {
        logger.log(Level.INFO, "Stopping server");

        try {
            serverSocket.close();

            logger.log(Level.FINE, "Shutting down executor service");

            executorService.shutdown();
            Thread.sleep(SHUTDOWN_TIME);

            if (!executorService.isShutdown()) {
                logger.log(Level.FINE, "Shutting down executor service now");

                executorService.shutdownNow();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Severe stop!" + "\n\t" + e.toString());

            //this really means stop lol
            System.exit(-1);
        }
    }

    private static class SocketListener extends Loop {
        private final ServerSocket serverSocket;

        public SocketListener(Callback<Exception> exceptionCallback, ServerSocket serverSocket) {
            super(exceptionCallback);
            this.serverSocket = serverSocket;
        }

        @Override
        public void loop() throws Exception {
            Socket socket = serverSocket.accept();
            SocketHandler handler = new SocketHandler(socket);
            handler.perform(socket);
        }
    }

    private static class SocketHandler implements Handler<Socket> {
        private final Socket socket;

        public SocketHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void perform(Socket obj) {
            //TODO: change it such that the field and the socket are not the same object, remove one or the other
        }
    }
}