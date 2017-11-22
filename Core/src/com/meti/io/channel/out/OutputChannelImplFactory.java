package com.meti.io.channel.out;

import com.meti.io.PipedStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/21/2017
 */
public class OutputChannelImplFactory {
    private OutputChannelImplFactory() {
    }

    public static OutputChannelImpl create(ObjectOutputStream outputStream) {
        return new OutputChannelImpl(outputStream);
    }

    public static OutputChannelImpl create(PipedStream pipedStream) {
        return new OutputChannelImpl(pipedStream.getObjectOutputStream());
    }

    public static class OutputChannelImpl implements OutputChannel {
        private final ObjectOutputStream outputStream;

        OutputChannelImpl(ObjectOutputStream outputStream) {
            this.outputStream = outputStream;
        }

        @Override
        public void write(Serializable serializable) throws IOException {
            outputStream.writeObject(serializable);
        }

        @Override
        public void writeAll(Serializable... serializables) throws IOException {
            write(serializables.length);

            //don't want anonymous class, try - catch
            for (Serializable serializable : serializables) {
                write(serializable);
            }
        }
    }
}
