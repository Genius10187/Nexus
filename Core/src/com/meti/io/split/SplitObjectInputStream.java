package com.meti.io.split;

import com.meti.io.PipedStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class SplitObjectInputStream {
    private boolean listening = false;

    private ObjectInputStream inputStream;

    private HashMap<Class, PipedStream> channelMap = new HashMap<>();

    public SplitObjectInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Runnable getRunnable() {
        listening = true;

        ListenRunnable runnable = new ListenRunnable();
        new Thread(runnable).start();
        return runnable;
    }

    private void createPipe(Class<?> c) throws IOException {
        //possible bug here, not sure if I understand this correctly
        //easy method
        channelMap.put(c, new PipedStream());
    }

    public ObjectInputStream forClass(Class<?> c) {
        if (listening) {
            //should we really consider returning the channel object instead?
            return channelMap.get(c).getObjectInputStream();
        } else {
            throw new IllegalStateException("Stream is not listening yet!");
        }
    }

    //consider listen method, if application doesn't have runnable
    private class ListenRunnable implements Runnable {
        @Override
        public void run() {
            listening = true;

            while (listening) {
                try {
                    Object obj = inputStream.readObject();
                    Class<?> c = obj.getClass();

                    ObjectOutputStream outputStream;

                    if (channelMap.containsKey(c)) {
                        outputStream = channelMap.get(c).getObjectOutputStream();
                    } else {
                        createPipe(c);
                        outputStream = channelMap.get(c).getObjectOutputStream();
                    }

                    outputStream.writeObject(obj);
                } catch (IOException | ClassNotFoundException e) {
                    //TODO: exception callback
                    e.printStackTrace();
                }
            }
        }
    }
}
