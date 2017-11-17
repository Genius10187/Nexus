package com.meti.util;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/7/2017
 */
public class Dialog {
    @FXML
    private TextArea messageArea;

    //making sure this method isn't called until messageArea has been initialized
    private void setMessageText(String messageText) {
        if (messageArea != null) {
            this.messageArea.setText(messageText);
        }
    }

    public void setMessageTextAndException(String messageText, Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        setMessageText(messageText + "\n\t" + writer.toString());
    }
}
