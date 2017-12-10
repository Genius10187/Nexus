package com.meti.server;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class ServerDisplay {
    @FXML
    private TextArea output;

    @FXML
    private Text activeThreadCountText;

    @FXML
    private Text socketCountText;

    @FXML
    private Text portText;

    private Server server;

    @FXML
    public void close() {
        server.stop();

        Platform.exit();
        System.exit(0);
    }

    public void buildListeners() {
        Handler handler = new TextAreaHandler();
        handler.setFormatter(new SimpleFormatter());
        handler.setLevel(Level.ALL);

        server.getLogger().addHandler(handler);

        AnimationTimer timer = new DisplayTimer();
        timer.start();
    }

    public void setServer(Server server) {
        this.server = server;

        portText.setText(String.valueOf(server.getServerSocket().getLocalPort()));
    }

    private class TextAreaHandler extends Handler {
        @Override
        public void publish(LogRecord record) {
            output.appendText(getFormatter().format(record) + "\n");
        }

        @Override
        public void flush() {

        }

        @Override
        public void close() throws SecurityException {

        }
    }

    private class DisplayTimer extends AnimationTimer {
        private int previousActiveThreadCount = -1;
        private int previousSocketCount = -1;

        @Override
        public void handle(long now) {
            int activeThreadCount = Thread.activeCount();
            if (previousActiveThreadCount == -1) {
                updateActiveThreadCount(activeThreadCount);
            } else {
                if (activeThreadCount != previousActiveThreadCount) {
                    updateActiveThreadCount(activeThreadCount);
                }
            }

            int socketCount = server.getSockets().size();
            if (previousSocketCount == -1) {
                updateSocketCount(socketCount);
            } else {
                if (socketCount != previousSocketCount) {
                    updateSocketCount(socketCount);
                }
            }
        }

        private void updateActiveThreadCount(int activeThreadCount) {
            activeThreadCountText.setText(String.valueOf(activeThreadCount));

            previousActiveThreadCount = activeThreadCount;
        }

        private void updateSocketCount(int socketCount) {
            socketCountText.setText(String.valueOf(socketCount));

            previousSocketCount = socketCount;
        }
    }
}
