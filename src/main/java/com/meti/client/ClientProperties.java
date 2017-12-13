package com.meti.client;

import com.meti.util.LoggerHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/12/2017
 */
public class ClientProperties {
    public static final Logger logger = Logger.getLogger("Application");
    public static final ExecutorService executorService = Executors.newCachedThreadPool();

    public static void log(Level level, Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        logger.log(level, writer.toString());
    }

    static {
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);

        Handler handler = new LoggerHandler();
        handler.setFormatter(new SimpleFormatter());
        handler.setLevel(Level.ALL);

        logger.addHandler(handler);

        logger.log(Level.INFO, "Initialized logger");
    }

    private ClientProperties() {
    }
}
