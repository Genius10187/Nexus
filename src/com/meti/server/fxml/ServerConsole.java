package com.meti.server.fxml;

import com.meti.Main;
import com.meti.server.Server;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2017
 */
public class ServerConsole {
    @FXML
    private TextField input;

    @FXML
    private TextArea output;

    //should do something about this server variable
    private Server server;

    @FXML
    public void nextInput() {
        Main.getInstance().getLogger().addHandler(new ConsoleHandler());
    }

    public void setServer(Server server) {
        this.server = server;
    }

    private class ConsoleHandler extends Handler {
        private final Formatter formatter = new SimpleFormatter();

        @Override
        public void publish(LogRecord record) {
            output.appendText(formatter.format(record));
        }

        @Override
        public void flush() {

        }

        @Override
        public void close() throws SecurityException {

        }
    }
}
