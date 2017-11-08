package com.meti.display;

import com.meti.Server;
import com.meti.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;

public class ServerDisplay {
    private Server server;

    @FXML
    private TextArea output;

    @FXML
    private TextField input;

    //we need even more fxml files for these two!
    @FXML
    public void openClients() {
        try {
            ClientsDisplay clientsDisplay = Utility.buildFXML(new File("Server\\assets\\fxml\\ClientsDisplay.fxml"), null);
            clientsDisplay.setServer(server);
        } catch (IOException e) {
            server.getConsole().log(e);
        }
    }

    @FXML
    public void openFiles() {
        //TODO:  openFiles
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
