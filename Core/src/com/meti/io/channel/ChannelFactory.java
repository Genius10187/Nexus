package com.meti.io.channel;

import com.meti.io.channel.in.InputChannelImplFactory.InputChannelImpl;
import com.meti.io.channel.out.OutputChannelImplFactory.OutputChannelImpl;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/26/2017
 */
public class ChannelFactory {
    private ChannelFactory() {
    }

    public static ChannelImpl createChannel(InputChannelImpl inputStream, OutputChannelImpl outputStream) {
        return new ChannelImpl(inputStream, outputStream);
    }

    public static class ChannelImpl implements Channel {
        private final InputChannelImpl inputStream;
        private final OutputChannelImpl outputStream;

        public ChannelImpl(InputChannelImpl inputStream, OutputChannelImpl outputStream) {
            this.inputStream = inputStream;
            this.outputStream = outputStream;
        }

        @Override
        public <T> T read(Class<T> c) {
            return null;
        }

        @Override
        public Serializable read() {
            return null;
        }

        @Override
        public Serializable[] readAll() {
            return new Serializable[0];
        }

        @Override
        public void write(Serializable serializable) {

        }

        @Override
        public void writeAll(Serializable... serializables) {

        }
    }
}
