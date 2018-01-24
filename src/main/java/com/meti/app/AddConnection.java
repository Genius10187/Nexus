package com.meti.app;

import com.meti.io.Sources;
import com.meti.io.connect.connections.ObjectConnection;
import com.meti.util.Loop;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import static com.meti.app.Main.appState;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/15/2018
 */
public class AddConnection implements Initializable {
    @FXML
    private TextField addressField;

    @FXML
    private TextField portField;

    @FXML
    private Text statusText;

    @FXML
    private Button nextButton;
    private AnimationTimer timer;
    private ObjectConnection connection;

    private String status;
    private boolean next;
    private Loop loop;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NewConnectionChangeListener listener = new NewConnectionChangeListener();
        addressField.textProperty().addListener(listener);
        portField.textProperty().addListener(listener);
        nextButton.setDisable(true);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                statusText.setText(status);
            }
        };
        timer.start();

        loop = new Loop() {
            @Override
            protected void loop() {
                if (next) {
                    buildSocket();

                    next = false;
                }
            }
        };

        appState.getService().submit(loop);
    }

    private void buildSocket() {
        String addressToken = addressField.getText();
        String portToken = portField.getText();
        if (addressToken != null && portToken != null) {
            StringBuilder statusBuilder = new StringBuilder();
            boolean addressValid = true;
            boolean portValid = true;

            InetAddress address1 = null;
            int port1 = 0;

            try {
                address1 = InetAddress.getByName(addressToken);
            } catch (UnknownHostException e) {
                addressValid = false;
            }

            try {
                port1 = Integer.parseInt(portToken);
            } catch (NumberFormatException e) {
                portValid = false;
            }

            if (addressValid || portValid) {
                if (!addressValid) {
                    statusBuilder.append("Invalid address!");
                    nextButton.setDisable(true);
                }

                if (!portValid) {
                    statusBuilder.append("Invalid port!");
                    nextButton.setDisable(true);
                }

                if (addressValid && portValid) {
                    try {
                        openConnection(address1, port1);
                        nextButton.setDisable(false);
                    } catch (IOException e) {
                        statusBuilder.append("Failed to open connection!");
                        nextButton.setDisable(true);
                    }
                }
            } else {
                statusBuilder.append("Invalid address and port!");
                nextButton.setDisable(true);
            }

            status = statusBuilder.toString();
        }
    }

    public void openConnection(InetAddress address, int port) throws IOException {
        statusText.setText("Opening a connection...");

        connection = new ObjectConnection(Sources.fromSocket(address, port));

        statusText.setText("Successfully established a connection!");
    }

    @FXML
    public void next() {
        loop.setRunning(false);
        timer.stop();

        appState.getContext().getPeer().startConnection(connection);
    }

    private class NewConnectionChangeListener implements ChangeListener<String> {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            next = true;
        }
    }
}
