package com.meti.util.concurrent;

import com.meti.util.intf.Callback;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/30/2017
 */
public abstract class Loop implements Runnable {

    protected final Callback<Exception> exceptionCallback;
    private Class<? extends Exception> previousExceptionClass = null;

    private boolean running = false;

    public Loop() {
        exceptionCallback = Throwable::printStackTrace;
    }

    public Loop(Callback<Exception> exceptionCallback) {
        this.exceptionCallback = exceptionCallback;
    }

    @Override
    public void run() {
        running = true;

        while (!Thread.interrupted() && running) {
            try {
                loop();
            } catch (Exception e) {
                if (previousExceptionClass != null && previousExceptionClass.equals(e.getClass())) {
                    return;
                } else {
                    previousExceptionClass = e.getClass();

                    try {
                        exceptionCallback.perform(e);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public abstract void loop() throws Exception;
}
