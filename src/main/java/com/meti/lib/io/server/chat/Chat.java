package com.meti.lib.io.server.chat;

import com.meti.app.Main;
import com.meti.app.View;
import com.meti.lib.io.client.Client;
import com.meti.lib.io.client.ClientState;
import com.meti.lib.io.server.command.Argument;
import com.meti.lib.io.server.command.ListCommand;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/20/2017
 */
public class Chat implements View {
    @FXML
    private TextField input;

    @FXML
    private TextArea output;

    private ClientState state;

    @Override
    public void setClientState(ClientState state) {
        this.state = state;
    }

    @Override
    public void init() throws IOException {
        Client client = state.getClient();

        List<?> chats = (List<?>) client.runCommand(new ListCommand(Argument.CHAT));
        chats.forEach((Consumer<Object>) o -> {
            if (o instanceof Message) {
                newChat((Message) o);
            }
        });

        new ChatTimer().start();
    }

    private void newChat(Message message) {
        output.appendText("[" + message.getName() + "]: " + message.getContent() + "\n");
    }

    @FXML
    public void nextInput() {
        try {
            String value = input.getText();
            Message message = new Message(state.getProperties().getProperty("username"), value);

            state.getClient().write(new ChatChange(message));
            state.getClient().flush();

            //clear the input
            input.setText("");
        } catch (IOException e) {
            Main.console.log(Level.WARNING, e);
        }
    }

    private class ChatTimer extends AnimationTimer {
        @Override
        public void handle(long now) {
            if (state.getClient().hasClass(ChatChange.class)) {
                ChatChange chatChange = state.getClient().readClass(ChatChange.class);
                Message content = chatChange.getContent();
                newChat(content);
            }
        }
    }
}
