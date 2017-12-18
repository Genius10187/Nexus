package com.meti.lib.io.client;

import com.meti.lib.io.server.asset.SplitInputStream;
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

    public Object readClass(Class<?> c) {
        return inputStream.pollClass(c);
    }

    public Object readSuperClass(Class<?> c) {
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

    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
    }
}
