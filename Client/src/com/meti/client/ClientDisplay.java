package com.meti.client;

import com.meti.io.Client;
import com.meti.io.Command;
import com.meti.util.Console;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;

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
    private Console console;
    private Client client;

    public void loadFromClientBuilder(ClientBuilder clientBuilder) throws IOException {
        this.clientBuilder = clientBuilder;
        this.console = clientBuilder.getConsole();
        this.client = clientBuilder.getClient();

        //occurs after initialization
        client.write(new Command("list", "filePaths"));
    }

    @FXML
    public void openToolTip() {
        //do something here
    }

    @FXML
    public void sendReport() {
        //TODO: send report
    }

    @FXML
    public void close() {
        Platform.exit();
    }

    @FXML
    public void openEditors() {
        //TODO: index editors
    }
}
