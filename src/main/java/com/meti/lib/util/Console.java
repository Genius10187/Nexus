package com.meti.lib.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
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

    public Console() {
        this("Console");
    }

    public Console(String name) {
        this(Logger.getLogger(name));
    }

    public Console(Logger logger) {
        this.logger = logger;

        setProperty(severe_shutdown, "true");
    }

    //delegate
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public void log(Level level, Exception exception) {
        StringWriter writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));
        log(level, writer.toString());
    }

    public void log(Level level, String message) {
        logger.log(level, message);

        if (Boolean.parseBoolean(properties.getProperty(severe_shutdown))) {
            System.exit(-1);
        }
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
