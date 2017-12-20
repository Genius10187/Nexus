package com.meti.lib.util;

import com.meti.app.InputStreamHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class Console {
    //properties here
    private static final String severe_shutdown = "SEVERE_SHUTDOWN";

    private final Properties properties = new Properties();
    private final Logger logger;

    //is basically final
    private InputStreamHandler inputStreamHandler;

    public Console() {
        this("Console");
    }

    public Console(String name) {
        this(Logger.getLogger(name));
    }

    public Console(Logger logger) {
        this.logger = logger;

        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);

        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);

        setProperty(severe_shutdown, "true");

        InputStreamHandler inputStreamHandler = buildInputStreamHandler();
        if (inputStreamHandler != null) {
            inputStreamHandler.setLevel(Level.ALL);
            logger.addHandler(inputStreamHandler);
        }

        this.inputStreamHandler = inputStreamHandler;
    }

    private InputStreamHandler buildInputStreamHandler() {
        try {
            return new InputStreamHandler();
        } catch (IOException e) {
            log(Level.WARNING, e);
        }
        return null;
    }

    public void log(Level level, Exception exception) {
        StringWriter writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));
        log(level, writer.toString());
    }

    public void log(Level level, String message) {
        logger.log(level, message);

        if (level.equals(Level.SEVERE) && Boolean.parseBoolean(properties.getProperty(severe_shutdown))) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.exit(-1);
        }
    }

    //delegate
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public InputStreamHandler getInputStreamHandler() {
        return inputStreamHandler;
    }

    public void log(Level level, String message, Exception exception) {
        StringWriter writer = new StringWriter();
        writer.append(message);
        writer.append("\n");
        exception.printStackTrace(new PrintWriter(writer));
        log(level, writer.toString());
    }

    public void addHandler(Handler handler) throws SecurityException {
        logger.addHandler(handler);
    }

    public Logger getLogger() {
        return logger;
    }

    public Properties getProperties() {
        return properties;
    }
}
