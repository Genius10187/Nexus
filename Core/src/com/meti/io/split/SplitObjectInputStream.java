package com.meti.io.split;

import java.io.*;
import java.util.HashMap;

public class SplitObjectInputStream {
    private boolean listening = false;

    private ObjectInputStream inputStream;

    private HashMap<Class, ObjectInputStream> objectInputStreamHashMap = new HashMap<>();
    private HashMap<Class, ObjectOutputStream> objectOutputStreamHashMap = new HashMap<>();

    public SplitObjectInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Runnable getRunnable() {
        listening = true;

        ListenRunnable runnable = new ListenRunnable();
        new Thread(runnable).start();
        return runnable;
    }

    public void createPipe(Class<?> c) throws IOException {
        //possible bug here, not sure if I understand this correctly
        PipedInputStream pipedInputStream = new PipedInputStream();
        PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);

        ObjectInputStream pipedObjectInputStream = new ObjectInputStream(pipedInputStream);
        ObjectOutputStream pipedObjectOutputStream = new ObjectOutputStream(pipedOutputStream);

        objectInputStreamHashMap.put(c, pipedObjectInputStream);
        objectOutputStreamHashMap.put(c, pipedObjectOutputStream);
    }

    public ObjectInputStream forClass(Class<?> c) {
        if (listening) {
            return objectInputStreamHashMap.get(c);
        } else {
            throw new IllegalStateException("Stream is not listening yet!");
        }
    }

    //consider listen method, if application doesn't have runnable

    public void setListening(boolean listening) {
        this.listening = listening;
    }

    private class ListenRunnable implements Runnable {
        @Override
        public void run() {
            listening = true;

            while (listening) {
                try {
                    Object obj = inputStream.readObject();
                    Class<?> c = obj.getClass();

                    ObjectOutputStream outputStream;

                    if (objectOutputStreamHashMap.containsKey(c)) {
                        outputStream = objectOutputStreamHashMap.get(c);
                    } else {
                        createPipe(c);
                        outputStream = objectOutputStreamHashMap.get(c);
                    }

                    outputStream.writeObject(obj);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
