package com.meti.util;

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
        Object obj = inputStream.readObject();
        if (!queueHashMap.containsKey(obj.getClass())) {
            buildQueue(obj.getClass());
        }

        Queue<Object> queue = queueHashMap.get(obj.getClass());
        queue.add(obj);
    }

    public Queue<Object> getQueue(Class<?> c) {
        if (!queueHashMap.containsKey(c)) {
            buildQueue(c);
        }

        return queueHashMap.get(c);
    }

    private void buildQueue(Class<?> c) {
        if (!queueHashMap.containsKey(c)) {
            queueHashMap.put(c, new PriorityQueue<>());
        }
    }
}
