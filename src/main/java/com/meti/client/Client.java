package com.meti.client;

import com.meti.asset.Asset;
import com.meti.command.Command;
import com.meti.command.ListCommand;
import com.meti.util.Cargo;
import com.meti.util.Handler;
import com.meti.util.Queuer;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

//called from ClientMain
public class Client implements Handler<Socket> {
    //edits for the master branch!

    private final Queuer queuer;
    private final Logger logger;
    private List<File> filePaths;
    private List<File> extensions;

    public Client(Logger logger, ExecutorService service, Socket socket) throws IOException {
        this.queuer = new Queuer(new ObjectInputStream(socket.getInputStream()));
        this.logger = logger;

        service.submit(queuer);
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

    public Asset getAsset(File location) {
        //Command command = new GetCommand();

        //TODO: handle get asset, use GetCommand
        return null;
    }

    public List<File> getFilePaths() {
        return filePaths;
    }

    public List<File> getExtensions() {
        return extensions;
    }

    public Logger getLogger() {
        return logger;
    }
}
