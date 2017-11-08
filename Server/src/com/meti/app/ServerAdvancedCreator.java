package com.meti.app;

import com.meti.util.Action;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/7/2017
 */
public class ServerAdvancedCreator {
    private ServerBuilder serverBuilder;
    private Action<String> onMaxQueueSizeChanged;
    private Action<String> onLocalAddress;

    private long sinceLast;

    @FXML
    private TextField localAddressField;

    @FXML
    private TextField maxQueueSizeField;

    @FXML
    public void localAddressChanged() {
        onLocalAddress.act(localAddressField.getText());
    }

    //method called every time one character is written to the field
    @FXML
    public void maxQueueSizeChanged() {
        onMaxQueueSizeChanged.act(maxQueueSizeField.getText());
    }

    public void setServerBuilder(ServerBuilder serverBuilder) {
        this.serverBuilder = serverBuilder;
    }

    public void setOnMaxQueueSizeChanged(Action<String> onMaxQueueSizeChanged) {
        this.onMaxQueueSizeChanged = onMaxQueueSizeChanged;
    }

    public void setOnLocalAddress(Action<String> onLocalAddress) {
        this.onLocalAddress = onLocalAddress;
    }
}
