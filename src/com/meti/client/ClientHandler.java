package com.meti.client;

import com.meti.util.Handler;

import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Handler<Socket> {
    private final Scanner scanner;

    public ClientHandler(Scanner scanner) {
        //TODO: handle client
        this.scanner = scanner;
    }

    @Override
    public void perform(Socket obj) {

    }
}
