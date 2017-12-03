package com.meti.server;

import com.meti.util.Callback;
import com.meti.util.LoggerHandler;
import com.meti.util.Loop;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/3/2017
 */
public class Server {
    public static final int SHUTDOWN_TIME = 5000;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private boolean running = false;
    private ServerSocket serverSocket;
    private Scanner systemScanner;
    private Logger logger = Logger.getLogger("Server");

    public void init(String line) {
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);

        Handler handler = new LoggerHandler();
        handler.setFormatter(new SimpleFormatter());
        handler.setLevel(Level.ALL);

        logger.addHandler(handler);
        logger.log(Level.INFO, "Initializing application");

        try {
            this.serverSocket = createSocket(line);

            Callback<Exception> callback = obj -> {
                StringWriter writer = new StringWriter();
                obj.printStackTrace(new PrintWriter(writer));
                logger.log(Level.WARNING, writer.toString());
            };

            File directory = new File("Server");
            if (!directory.exists()) {
                logger.log(Level.FINE, "Directory status creation: " + directory.mkdirs());
            }

            System.out.println("Loaded files: ");
            for (File f : AssetManager.load(directory)) {
                System.out.println("\n\t" + f.getPath());
            }

            SocketListener listener = new SocketListener(callback, serverSocket, this);
            executorService.submit(listener);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in initializing util " + e);
            System.exit(-1);
        }
    }

    public ServerSocket createSocket(String input) throws IOException {
        int port = Integer.parseInt(input);
        return new ServerSocket(port);
    }

    public void run() {
        logger.log(Level.INFO, "Running application");

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

    private void stop() {
        logger.log(Level.INFO, "Stopping application");

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

    private class SocketListener extends Loop {
        private final ServerSocket serverSocket;
        private final Server server;

        public SocketListener(Callback<Exception> exceptionCallback, ServerSocket serverSocket, Server server) {
            super(exceptionCallback);
            this.serverSocket = serverSocket;
            this.server = server;
        }

        @Override
        public void loop() throws Exception {
            Socket socket = serverSocket.accept();
            SocketHandler handler = new SocketHandler(executorService, exceptionCallback, socket, server);
            handler.perform(socket);
        }
    }
}
