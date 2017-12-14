package com.meti.server;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AdvancedServerCreator {
    @FXML
    private TextField maxQueueSizeField;

    @FXML
    private TextField addressField;

    public TextField getMaxQueueSizeField() {
        return maxQueueSizeField;
    }

    public TextField getAddressField() {
        return addressField;
    }
}
