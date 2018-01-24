package com.meti.app;

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
import java.net.URL;
import java.util.ResourceBundle;

import static com.meti.app.Main.appState;

public class AddListener implements Initializable {
    @FXML
    private TextField portField;

    @FXML
    private Text statusText;

    @FXML
    private Button nextButton;

    private AnimationTimer timer;
    private boolean next;
    private String status;
    private Loop loop;
    private int port;

    @FXML
    public void openAdvancedSettings() {
        //TODO: listener advanced settings
    }

    @FXML
    public void next() {
        loop.setRunning(false);
        timer.stop();

        try {
            appState.getContext().getPeer().startListening(port, ObjectConnection.class);
        } catch (IOException e) {
            status = "Cannot host at port " + port + "!";
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        portField.textProperty().addListener(new AddListenerChangeListener());
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
            protected void loop() throws Exception {
                if (next) {
                    buildListener();

                    next = false;
                }
            }
        };

        appState.getService().submit(loop);
    }

    private void buildListener() {
        String portToken = portField.getText();
        try {
            port = Integer.parseInt(portToken);
            nextButton.setDisable(false);
        } catch (NumberFormatException e) {
            status = "Invalid port!";
            nextButton.setDisable(true);
        }
    }

    private class AddListenerChangeListener implements ChangeListener<String> {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            next = true;
        }
    }
}
