package com.meti.io.split;

import com.meti.io.PipedStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/21/2017
 */
public class SplitObjectOutputStream {
    private final ArrayList<ObjectInputStream> inputStreams = new ArrayList<>();
    private final ObjectOutputStream outputStream;

    private final PipedStream defaultChannel;

    public SplitObjectOutputStream(ObjectOutputStream outputStream) throws IOException {
        this.outputStream = outputStream;

        this.defaultChannel = new PipedStream();
    }

    public Runnable buildDefaultChannel() {
        return new ListenRunnable(defaultChannel.getObjectInputStream());
    }

    public Runnable bind(ObjectInputStream inputStream) {
        inputStreams.add(inputStream);

        return new ListenRunnable(inputStream);
    }

    public PipedStream getDefaultChannel() {
        return defaultChannel;
    }

    private class ListenRunnable implements Runnable {
        private ObjectInputStream inputStream;
        private boolean running;

        private ListenRunnable(ObjectInputStream inputStream) {
            this.inputStream = inputStream;
        }

        //TODO: handle running

        @Override
        public void run() {
            while (running) {
                try {
                    //we are just passing all the results from the input stream to the output stream
                    Object obj = inputStream.readObject();
                    outputStream.writeObject(obj);
                } catch (IOException | ClassNotFoundException e) {
                    //Method should never be thrown, class should always be present
                    //however, the output stream might throw an exception

                    //TODO: console callback listenRunnable soos
                    e.printStackTrace();
                }
            }
        }
    }
}
