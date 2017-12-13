package com.meti.util;

import com.meti.command.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/5/2017
 */
public class Queuer extends Loop {

    private final HashMap<Class<?>, Queue<Object>> queueHashMap = new HashMap<>();
    private final ObjectInputStream inputStream;

    public Queuer(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void loop() throws Exception {
/*
        Object obj = inputStream.readObject();
        if (!queueHashMap.containsKey(obj.getClass())) {
            buildQueue(obj.getClass());
        }

        Queue<Object> queue = queueHashMap.get(obj.getClass());
        queue.add(obj);
*/

        try {
            Object obj = inputStream.readObject();

            Class objectClass = obj.getClass();

            if (obj instanceof Command) {
                add(Command.class, obj);
            } else if (obj instanceof Change) {
                add(Change.class, obj);
            } else {
                add(objectClass, obj);
            }

        } catch (IOException | ClassNotFoundException e) {
            setRunning(false);
        }
    }

    public void add(Class<?> c, Object obj) {
        buildQueue(c);

        queueHashMap.get(c).add(obj);
    }

    private void buildQueue(Class<?> c) {
        if (!queueHashMap.containsKey(c)) {
            queueHashMap.put(c, new PriorityQueue<>());
        }
    }

    public <T> T poll(Class<T> c) {
        buildQueue(c);

        while (queueHashMap.get(c).size() == 0) {
            //we wait?
        }

        return (T) queueHashMap.get(c).poll();
    }
}
