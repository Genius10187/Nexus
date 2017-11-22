package com.meti.io.channel.in;

import com.meti.io.PipedStream;
import com.meti.util.Utility;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/21/2017
 */
public class InputChannelImplFactory {
    private InputChannelImplFactory() {
    }

    public static InputChannelImpl create(ObjectInputStream inputStream) {
        return new InputChannelImpl(inputStream);
    }

    public static InputChannelImpl create(PipedStream pipedStream) {
        return new InputChannelImpl(pipedStream.getObjectInputStream());
    }

    public static class InputChannelImpl implements InputChannel {
        private final ObjectInputStream inputStream;

        InputChannelImpl(ObjectInputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public <T> T read(Class<T> c) throws IOException, ClassNotFoundException {
            return Utility.castIfOfInstance(read(), c);
        }

        @Override
        public Serializable read() throws IOException, ClassNotFoundException {
            return (Serializable) inputStream.readObject();
        }

        @Override
        public Serializable[] readAll() throws IOException, ClassNotFoundException {
            int size = read(Integer.class);

            Serializable[] array = new Serializable[size];
            for (int i = 0; i < size; i++) {
                array[i] = read();
            }

            return array;
        }
    }

}
