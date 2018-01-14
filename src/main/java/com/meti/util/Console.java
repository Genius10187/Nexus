package com.meti.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/14/2018
 */
public class Console {
    private final Logger logger;

    public Console() {
        this.logger = Logger.getLogger("Default");
    }

    public Console(Class<?> c) {
        this(c.getName());
    }

    public Console(String name) {
        this.logger = Logger.getLogger(name);
    }

    public boolean log(Level level, String message) {
        return log(level, message, null);
    }

    public boolean log(Level level, String message, Exception exception) {
        if (message != null || exception != null) {
            StringBuilder builder = new StringBuilder();
            if (message != null) {
                builder.append(message);
            }

            if (message != null && exception != null) {
                builder.append("\n");
            }

            if (exception != null) {
                StringWriter writer = new StringWriter();
                exception.printStackTrace(new PrintWriter(writer));

                builder.append(writer.toString());
            }

            logger.log(level, builder.toString());
            return true;
        } else {
            return false;
        }
    }

    public boolean log(Level level, Exception exception) {
        return log(level, null, exception);
    }
}
