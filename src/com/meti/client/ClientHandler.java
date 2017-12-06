package com.meti.client;

import com.meti.command.Command;
import com.meti.command.ListCommand;
import com.meti.util.Cargo;
import com.meti.util.Handler;
import com.meti.util.Queuer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

//called from ClientMain
public class ClientHandler implements Handler<Socket> {
    //edits for the master branch!

    private final Queuer queuer;
    private final Logger logger;

    public ClientHandler(Logger logger, ExecutorService service, Socket socket) throws IOException {
        this.queuer = new Queuer(new ObjectInputStream(socket.getInputStream()));
        this.logger = logger;

        service.submit(queuer);
    }

    @Override
    public void perform(Socket obj) throws IOException, ClassNotFoundException {
        ObjectOutputStream outputStream = new ObjectOutputStream(obj.getOutputStream());

        logger.log(Level.FINE, "Writing initialization commands");

        {
            Command command = new ListCommand(ListCommand.TYPE_FILES);
            outputStream.writeObject(command);
            outputStream.flush();


            //cargo not being received
            Cargo cargo = queuer.poll(Cargo.class);
            System.out.println(cargo.getContents());
        }

        {
            Command command = new ListCommand(ListCommand.TYPE_EXTENSIONS);
            outputStream.writeObject(command);
            outputStream.flush();

            Cargo cargo = queuer.poll(Cargo.class);
            System.out.println(cargo.getContents());
        }

        logger.log(Level.FINE, "Successfully received initialization cargo");
    }
}
