package com.meti.lib.io.client;

import com.meti.lib.io.server.SplitInputStream;
import com.meti.lib.io.server.asset.ListCommand;
import com.meti.lib.io.source.ObjectSource;
import com.meti.lib.util.execute.Executable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class Client implements Executable {
    private final SplitInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public Client(ObjectSource objectSource) {
        this.inputStream = new SplitInputStream(objectSource.getInputStream());
        this.outputStream = objectSource.getOutputStream();
    }

    @Override
    public Callable[] getCallables() {
        return new Callable[0];
    }

    @Override
    public Runnable[] getRunnables() {
        return new Runnable[]{
                inputStream
        };
    }

    public boolean hasClass(Class c) {
        return inputStream.hasClass(c);
    }

    public boolean hasSuperClass(Class<?> c) {
        return inputStream.hasSuperClass(c);
    }

    public <T> T readClass(Class<T> c) {
        return inputStream.pollClass(c);
    }

    public <T> T readSuperClass(Class<T> c) {
        return inputStream.pollSuperClass(c);
    }

    public void writeAll(Object... objects) throws IOException {
        if (objects.length == 0) {
            throw new IllegalArgumentException("Objects do not exist");
        }

        for (Object obj : objects) {
            write(obj);
        }
    }

    public void write(Object obj) throws IOException {
        outputStream.writeObject(obj);
    }

    public void flush() throws IOException {
        outputStream.flush();
    }

    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
    }

    public Object runCommand(ListCommand command) throws IOException {
        write(command);
        flush();

        //TODO: if runCommand is not of returnable type

        //might have high cpu usage here
        boolean contains;
        do {
            contains = hasSuperClass(Object.class);
        } while (!contains);
        return readSuperClass(Object.class);
    }
}
