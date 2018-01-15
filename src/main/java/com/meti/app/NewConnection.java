package com.meti.app;

import com.meti.io.Sources;
import com.meti.io.connect.connections.ObjectConnection;
import javafx.animation.AnimationTimer;
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
public class NewConnection implements Initializable {
    @FXML
    private TextField addressField;

    @FXML
    private TextField portField;

    @FXML
    private Text status;

    @FXML
    private Button nextButton;
    private AnimationTimer timer;
    private ObjectConnection connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                String addressToken = addressField.getText();
                String portToken = portField.getText();
                if (addressToken != null && portToken != null) {
                    StringBuilder statusBuilder = new StringBuilder();
                    boolean addressValid = true;
                    boolean portValid = true;

                    InetAddress address = null;
                    int port = 0;

                    try {
                        address = InetAddress.getByName(addressToken);
                    } catch (UnknownHostException e) {
                        addressValid = false;
                    }

                    try {
                        port = Integer.parseInt(portToken);
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
                                openConnection(address, port);
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

                    status.setText(statusBuilder.toString());
                }
            }
        };

        timer.start();
    }

    public void openConnection(InetAddress address, int port) throws IOException {
        status.setText("Opening a connection...");

        connection = new ObjectConnection(Sources.fromSocket(address, port));

        status.setText("Successfully established a connection!");
    }

    @FXML
    public void next() {
        timer.stop();

        appState.getContext().getPeer().initConnection(connection);
    }
}
