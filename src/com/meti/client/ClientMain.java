package com.meti.client;

import com.meti.util.LoggerHandler;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ClientMain {
    private static final Logger logger = Logger.getLogger("Application");

    public static void main(String[] args) {
        init();
        run();
    }

    public static void init() {
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);

        Handler handler = new LoggerHandler();
        handler.setFormatter(new SimpleFormatter());
        handler.setLevel(Level.ALL);

        logger.addHandler(handler);
        logger.log(Level.INFO, "Initializing application");

        try {

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in initializing util " + e);
            System.exit(-1);
        }
    }

    public static void run() {
        logger.log(Level.INFO, "Running application");
    }

    public static void stop() {
        //TODO: handle client stop
    }
}
