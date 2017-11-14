package com.meti.client;

import com.meti.io.Client;
import com.meti.util.Console;
import com.meti.util.Utility;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ClientBuilder {
    private final Console console;
    private Client client;

    public ClientBuilder(Console console) {
        this.console = console;
    }

    public void openCreatorDialog(Stage primaryStage) throws IOException {
        console.log("Opening dialog");

        ClientCreator creator = Utility.buildFXML(new File("Client\\assets\\fxml\\ClientCreator.fxml"), primaryStage);
        creator.setClientBuilder(this);
    }

    public Console getConsole() {
        return console;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void openDisplayDialog() throws IOException, ClassNotFoundException {
        ClientDisplay display = Utility.buildFXML(new File("Client\\assets\\fxml\\ClientDisplay.fxml"), null);
        display.loadFromClientBuilder(this);
    }

    public Client getClient() {
        return client;
    }
}
