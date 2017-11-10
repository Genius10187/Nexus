package com.meti.display;

import com.meti.Server;
import com.meti.io.Command;
import com.meti.util.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

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
        try {
            FilesDisplay filesDisplay = Utility.buildFXML(new File("Server\\assets\\fxml\\FilesDisplay.fxml"), null);
            filesDisplay.setServer(server);
        } catch (IOException e) {
            server.getConsole().log(e);
        }
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

        String[] args = line.split(" ");

        Command command = new Command(args[0], Arrays.copyOfRange(args, 1, args.length));
        server.runCommand(command);
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
