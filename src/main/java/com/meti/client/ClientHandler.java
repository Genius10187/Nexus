package com.meti.client;

import com.meti.command.Command;
import com.meti.command.ListCommand;
import com.meti.util.concurrent.Queuer;
import com.meti.util.intf.Handler;
import com.meti.util.io.Cargo;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.meti.client.ClientProperties.executorService;
import static com.meti.client.ClientProperties.logger;

//called from ClientMain
public class ClientHandler implements Handler<Socket> {

    private final Queuer queuer;
    private List<File> filePaths;
    private List<File> extensions;

    public ClientHandler(Socket socket) throws IOException {
        this.queuer = new Queuer(new ObjectInputStream(socket.getInputStream()));

        executorService.submit(queuer);
    }

    @Override
    public void perform(Socket obj) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(obj.getOutputStream());

        logger.log(Level.FINE, "Writing initialization commands");

        {
            Command command = new ListCommand(ListCommand.TYPE_FILES);
            outputStream.writeObject(command);
            outputStream.flush();

            //cargo not being received
            Cargo cargo = queuer.poll(Cargo.class);

            filePaths = new ArrayList<>();
            cargo.getContents().forEach(o -> filePaths.add((File) o));
        }

        {
            Command command = new ListCommand(ListCommand.TYPE_EXTENSIONS);
            outputStream.writeObject(command);
            outputStream.flush();

            Cargo cargo = queuer.poll(Cargo.class);

            extensions = new ArrayList<>();
            cargo.getContents().forEach(o -> extensions.add((File) o));
        }

        logger.log(Level.FINE, "Successfully received initialization cargo");
    }

    public List<File> getFilePaths() {
        return filePaths;
    }

    //TODO: Returns the extensions that can be processed by the server.
    public List<File> getExtensions() {
        return extensions;
    }

    public Logger getLogger() {
        return logger;
    }
}
