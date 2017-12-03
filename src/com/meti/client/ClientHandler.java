package com.meti.client;

import com.meti.command.Command;
import com.meti.command.ListCommand;
import com.meti.util.Cargo;
import com.meti.util.Handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

//called from ClientMain
public class ClientHandler implements Handler<Socket> {
    private final Scanner scanner;

    public ClientHandler(Scanner scanner) {
        //TODO: handle client
        this.scanner = scanner;
    }

    @Override
    public void perform(Socket obj) throws IOException, ClassNotFoundException {
        ObjectOutputStream outputStream = new ObjectOutputStream(obj.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(obj.getInputStream());

        {
            Command command = new ListCommand(ListCommand.TYPE_FILES);
            outputStream.writeObject(command);
            outputStream.flush();

            Cargo cargo = (Cargo) inputStream.readObject();
            System.out.println(cargo.getContents());
        }

        {
            Command command = new ListCommand(ListCommand.TYPE_EXTENSIONS);
            outputStream.writeObject(command);
            outputStream.flush();

            Cargo cargo = (Cargo) inputStream.readObject();
            System.out.println(cargo.getContents());
        }
    }
}
