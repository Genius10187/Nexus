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
import java.net.SocketException;
import java.util.ArrayList;
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
    private final ArrayList<Socket> sockets = new ArrayList<>();

    //TODO: running really necessary?
    private boolean running = false;
    private ServerSocket serverSocket;
    private Logger logger = Logger.getLogger("Server");

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;

        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);

        Handler handler = new LoggerHandler();
        handler.setFormatter(new SimpleFormatter());
        handler.setLevel(Level.ALL);

        logger.addHandler(handler);
    }

    public void run() {
        logger.log(Level.INFO, "Initializing application");

        try {
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

    public void stop() {
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

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public Logger getLogger() {
        return logger;
    }

    public ArrayList<Socket> getSockets() {
        return sockets;
    }

    public ExecutorService getExecutorService() {
        return executorService;
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
        public void loop() throws IOException {
            try {
                Socket socket = serverSocket.accept();

                sockets.add(socket);
                SocketHandler handler = new SocketHandler(executorService, exceptionCallback, socket, server);
                handler.perform(socket);
            } catch (SocketException e) {
                if (!serverSocket.isClosed()) {
                    serverSocket.close();
                }

                setRunning(false);
            }
        }
    }
}
