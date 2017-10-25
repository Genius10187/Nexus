package com.meti.server.fxml;

import com.meti.server.Server;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import static com.meti.Main.getInstance;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/20/2017
 */
public class ServerCreator implements Initializable {
    private static final Random passwordRandom = new Random();

    private static final String[] optionStrings = {
            "Port",
            "Password"
    };

    @FXML
    private TextField port;
    @FXML
    private TextField password;
    @FXML
    private ChoiceBox<String> options;

    //usually null!!
    private Server server;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        options.getItems().addAll(optionStrings);
    }

    public void start() {
        getInstance().buildStage("assets\\fxml\\ServerCreator.fxml", null);
    }

    //not sure if fxml methods should really have exceptions thrown...
    @FXML
    public void generateField() throws IOException {
        switch (options.getSelectionModel().getSelectedItem()) {
            case "Port":
                /*
                generate random port, need to have server object pre-allocated...
                should be able to client consecutively random ports
                check for possible bugs, otherwise
                 */
                server = new Server(0);
                port.setText(String.valueOf(server.getServerSocket().getLocalPort()));
                break;
            case "Password":
                //generate random numeric password
                password.setText(String.valueOf(passwordRandom.nextLong()));
                break;
        }
    }

    //again, not sure if fxml methods should have exception throwing
    @FXML
    public void connect() throws IOException, IllegalAccessException, InstantiationException {
        //could be generated in generateField
        if (server == null) server = new Server(Integer.parseInt(port.getText()));

        //inspection says could produce NullPointerException, analysis of source code
        //implies inspection incorrect
        server.listen();

        //might have security issues here...
        server.setPassword(password.getText());

        getInstance().addStoppable(server);

        Object controller = getInstance().buildStage("assets\\fxml\\ServerDisplay.fxml", null);
        if (controller instanceof ServerDisplay) {
            ((ServerDisplay) controller).setServer(server);
        } else {
            throw new RuntimeException("Controller not instance of ServerDisplay");
        }
    }
}
