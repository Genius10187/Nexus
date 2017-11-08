package com.meti.display;

import com.meti.Server;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ServerDisplay {
    private Server server;

    @FXML
    private TextArea output;

    @FXML
    private TextField input;

    //we need even more fxml files for these two!
    @FXML
    public void openClients() {

    }

    @FXML
    public void openFiles() {

    }

    //not sure if this is a good idea or not, especially
    //because the user could spam us... never trust
    //the users
    @FXML
    public void sendReport() {
        //TODO: send report
    }

    @FXML
    public void nextInput() {
        String line = input.getText();

        //reset the input token
        input.setText("");

        //TODO: handle input
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
