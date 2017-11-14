package com.meti;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/12/2017
 */
public class ClientDisplay {
    @FXML
    private ListView<String> fileView;

    @FXML
    private Tooltip editToolTip;

    @FXML
    private ListView<String> editorOptions;

    @FXML
    private GridPane fileDataPane;

    @FXML
    private Text name;

    @FXML
    private Text size;

    @FXML
    private Text supported;

    private ClientBuilder clientBuilder;

    //TODO: flesh out clientDisplay

    public void setClientBuilder(ClientBuilder clientBuilder) {
        this.clientBuilder = clientBuilder;
    }

    @FXML
    public void openToolTip() {
    }

    @FXML
    public void sendReport() {

    }

    @FXML
    public void close() {

    }

    @FXML
    public void openEditors() {

    }
}
