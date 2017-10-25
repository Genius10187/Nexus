package com.meti.server.fxml;

import com.meti.Main;
import com.meti.server.Server;
import com.meti.server.util.Command;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2017
 */
public class ServerConsole implements Initializable {
    @FXML
    private TextField input;

    @FXML
    private TextArea output;

    //should do something about this server variable
    private Server server;

    @FXML
    public void nextInput() throws IOException {
        String[] args = input.getText().split(" ");
        Command command = new Command(args[0], Arrays.copyOfRange(args, 1, args.length));
        server.runCommand(command);
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.getInstance().getLogger().addHandler(new ConsoleHandler());
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
