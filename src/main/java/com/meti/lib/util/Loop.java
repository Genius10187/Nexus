package com.meti.lib.util;

import com.meti.lib.util.callback.Callback;
import com.meti.lib.util.callback.DefaultCallback;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/18/2017
 */
public abstract class Loop implements Runnable {
    private Callback callback = new DefaultCallback();
    private boolean running = false;

    @Override
    public void run() {
        setRunning(true);

        init();
        while (!Thread.interrupted() && running) {
            try {
                loop();
            } catch (Exception e) {
                callback.act(e);
            }
        }
    }

    public void init() {
    }

    public abstract void loop() throws Exception;

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
