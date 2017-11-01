package com.meti.util;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class Dialog {
    @FXML
    private TextArea message;


    public void setMessage(String message) {
        this.message.setText(message);
    }
}
