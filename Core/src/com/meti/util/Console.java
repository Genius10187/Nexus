package com.meti.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/7/2017
 */
public class Console {
    private final Logger logger = Logger.getLogger("Application");

    {
        logger.setLevel(Level.ALL);
    }

    public Console() {
    }

    public void log(String message) {
        log(Level.INFO, message);
    }

    //delegate method
    public void log(Level level, String message) {
        logger.log(level, message);
    }

    public void log(Exception e) {
        //assume it is a warning, system should not terminate
        log(Level.WARNING, e);
    }

    public void log(Level level, Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        log(level, writer.toString());
    }

    public void addHandler(Handler handler) {
        logger.addHandler(handler);
    }
}
