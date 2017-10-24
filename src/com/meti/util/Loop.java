package com.meti.util;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/20/2017
 */
public abstract class Loop implements Runnable {
    private boolean running = true;

    @Override
    public void run(){
        while(running){
            loop();
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public abstract void loop();
}
