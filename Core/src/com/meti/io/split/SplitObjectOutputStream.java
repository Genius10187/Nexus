package com.meti.io.split;

import com.meti.io.PipedStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/21/2017
 */
public class SplitObjectOutputStream {
    private final HashMap<Class<?>, PipedStream> pipedStreams = new HashMap<>();
    private final ObjectOutputStream outputStream;

    public SplitObjectOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public ObjectOutputStream forClass(Class<?> c, ExecutorService service) throws IOException {
        if (pipedStreams.containsKey(c)) {
            return pipedStreams.get(c).getObjectOutputStream();
        } else {
            PipedStream pipedStream = new PipedStream();
            ListenRunnable runnable = new ListenRunnable(pipedStream.getObjectInputStream());
            service.submit(runnable);

            pipedStreams.put(c, pipedStream);
            return pipedStream.getObjectOutputStream();
        }
    }

    private class ListenRunnable implements Runnable {
        private ObjectInputStream inputStream;
        private boolean running = true;

        private ListenRunnable(ObjectInputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    //we are just passing all the results from the input stream to the output stream
                    Object obj = inputStream.readObject();
                    outputStream.writeUnshared(obj);
                    outputStream.flush();
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
