package com.meti.lib.io.server;

import com.meti.lib.io.server.chat.Message;
import java.util.ArrayList;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/22/2017
 */
public class ChatState {
    private final ArrayList<Message> messages = new ArrayList<>();

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void add(Message message) {
        messages.add(message);
}
