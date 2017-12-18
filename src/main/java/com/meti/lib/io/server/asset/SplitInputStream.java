package com.meti.lib.io.server.asset;

import com.meti.lib.util.Loop;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public class SplitInputStream extends Loop {
    private final ObjectInputStream inputStream;
    private final ConcurrentHashMap<Class, Queue<Object>> queueHashMap = new ConcurrentHashMap<>();

    public SplitInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void loop() throws Exception {
        Object obj = inputStream.readObject();
        Class<?> objClass = obj.getClass();
        if (queueHashMap.containsKey(objClass)) {
            queueHashMap.get(objClass).add(obj);
        } else {
            Queue<Object> queue = new PriorityQueue<>();
            queue.add(obj);
            queueHashMap.put(objClass, queue);
        }
    }

    public Object pollClass(Class c) {
        checkLoopRunning();

        return queueHashMap.get(c).poll();
    }

    private void checkLoopRunning() {
        if (!isRunning()) {
            throw new IllegalStateException("Not reading from stream, cannot poll object");
        }
    }

    public Object pollSuperClass(Class<?> c) {
        checkLoopRunning();

        if (queueHashMap.size() == 0) {
            throw new IllegalStateException("No internal queues found");
        }

        for (Class key : queueHashMap.keySet()) {
            if (c.isAssignableFrom(key)) {
                return queueHashMap.get(key).poll();
            }
        }

        throw new IllegalArgumentException("Internal queues do not contain class " + c.getName());
    }

    public void close() throws IOException {
        inputStream.close();
    }
}
