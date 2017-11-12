package com.meti.util;

import javafx.animation.AnimationTimer;

import java.util.PriorityQueue;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/12/2017
 */
public abstract class BufferedTimer<T> extends AnimationTimer implements Requestable<T> {
    private final PriorityQueue<Request<T>> queue = new PriorityQueue<>();

    @Override
    public void onRequest(Request<T> request) {
        queue.add(request);
    }

    @Override
    public void handle(long now) {
        if (queue.size() != 0) {
            onRequestFromBuffer(queue.poll());
        }
    }

    protected abstract void onRequestFromBuffer(Request<T> request);
}
