package com.meti.lib.io.server;

import java.util.ArrayList;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/22/2017
 */
public class ChatState {
    private final ArrayList<String> chats = new ArrayList<>();

    public ArrayList<String> getChats() {
        return chats;
    }

    public void add(String content) {
        chats.add(content);
    }
}
