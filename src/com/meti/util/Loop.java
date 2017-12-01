package com.meti.util;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/30/2017
 */
public abstract class Loop implements Runnable {
    private final Callback<Exception> exceptionCallback;
    private Class<? extends Exception> previousExceptionClass = null;

    public Loop() {
        exceptionCallback = Throwable::printStackTrace;
    }

    public Loop(Callback<Exception> exceptionCallback) {
        this.exceptionCallback = exceptionCallback;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                loop();
            } catch (Exception e) {
                if (previousExceptionClass != null && previousExceptionClass.equals(e.getClass())) {
                    return;
                } else {
                    previousExceptionClass = e.getClass();

                    exceptionCallback.perform(e);
                }
            }
        }
    }

    public abstract void loop() throws Exception;
}
