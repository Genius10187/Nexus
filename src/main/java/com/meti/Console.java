package com.meti;

import javafx.application.Platform;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/30/2017
 */
public class Console {
    private final Logger logger;

    public Console(String name) {
        this.logger = Logger.getLogger(name);

        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);

        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
    }

    public void log(Level level, String message, Exception e) {
        StringWriter writer = new StringWriter();
        writer.append(message);
        writer.append("\n");
        e.printStackTrace(new PrintWriter(writer));
        log(level, writer.toString());
    }

    public void log(Level level, String message) {
        logger.log(level, message);

        if (level.equals(Level.SEVERE)) {
            Platform.exit();
        }
    }

    public void log(Level level, Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        log(level, writer.toString());
    }

    public Logger getLogger() {
        return logger;
    }
}
