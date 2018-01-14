package com.meti.app;

import com.meti.util.Controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/14/2018
 */
public class Chat extends Controller implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TextArea area = new TextArea();
        //TODO: replace area with RichTextFX API in chat

        anchorPane.getChildren().add(area);
    }
}
