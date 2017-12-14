package com.meti.client;

import com.meti.asset.Asset;
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

import static com.meti.client.ClientProperties.*;

//called from ClientMain
public class Client implements Handler<Socket> {
    //edits for the master branch!

    private final Queuer queuer;
    private List<File> filePaths;
    private List<File> extensions;

    public Client(Socket socket) throws IOException {
        this.queuer = new Queuer(new ObjectInputStream(socket.getInputStream()));

        executorService.submit(queuer);
    }

    @Override
    public void perform(Socket obj) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(obj.getOutputStream());

        try {
            getFilePaths(outputStream);
            getExtensions(outputStream);

            logger.log(Level.FINE, "Successfully received initialization cargo");
        } catch (IOException e) {
            log(Level.SEVERE, e);
        }
    }

    private void getFilePaths(ObjectOutputStream outputStream) throws IOException {
        Command command = new ListCommand(ListCommand.TYPE_FILES);
        writeObject(outputStream, command);

        //cargo not being received
        Cargo cargo = queuer.poll(Cargo.class);

        filePaths = new ArrayList<>();
        cargo.getContents().forEach(o -> filePaths.add((File) o));
    }

    private void getExtensions(ObjectOutputStream outputStream) throws IOException {
        Command command = new ListCommand(ListCommand.TYPE_EXTENSIONS);
        writeObject(outputStream, command);

        Cargo cargo = queuer.poll(Cargo.class);

        extensions = new ArrayList<>();
        cargo.getContents().forEach(o -> extensions.add((File) o));
    }

    private void writeObject(ObjectOutputStream outputStream, Object object) throws IOException {
        outputStream.writeObject(object);
        outputStream.flush();
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
}
