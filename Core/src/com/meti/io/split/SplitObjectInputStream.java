package com.meti.io.split;

import com.meti.io.PipedStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
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

    public ObjectInputStream forClass(Class<?> c) throws IOException {
        if (listening) {
            //should we really consider returning the channel object instead?
            if (channelMap.containsKey(c)) {
                return channelMap.get(c).getObjectInputStream();
            } else {
                createPipe(c);
                return channelMap.get(c).getObjectInputStream();
            }
        } else {
            throw new IllegalStateException("Stream is not listening yet!");
        }
    }

    private void createPipe(Class<?> c) throws IOException {
        //possible bug here, not sure if I understand this correctly
        //easy method
        channelMap.put(c, new PipedStream());
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
                } catch (SocketException e) {
                    //we exit the loop
                    e.printStackTrace();
                    break;
                } catch (IOException | ClassNotFoundException e) {
                    //TODO: exception callback
                    e.printStackTrace();
                }
            }
        }
    }
}
