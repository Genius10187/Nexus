package com.meti.app;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import static com.meti.app.Main.console;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/20/2017
 */
public class Console implements Initializable {
    @FXML
    private TextArea output;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AnimationTimer timer = new AnimationTimer() {
            private InputStream inputStream = Main.console.getInputStreamHandler().getInputStream();

            @Override
            public void handle(long now) {
                try {
                    if (inputStream.available() > 0) {
                        output.appendText((char) inputStream.read() + "");
                    }
                } catch (IOException e) {
                    console.log(Level.WARNING, e);
                }
            }
        };

        timer.start();
    }

}
