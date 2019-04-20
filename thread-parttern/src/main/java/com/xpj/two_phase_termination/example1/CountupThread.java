package com.xpj.two_phase_termination.example1;

import java.util.concurrent.TimeUnit;

public class CountupThread extends Thread{
    private long counter;
    private volatile boolean shutdownRequested = false;

    public void shutdownRequest() {
        shutdownRequested = true;
        interrupt();
    }

    public boolean isShutdownRequested() {
        return shutdownRequested;
    }

    @Override
    public void run() {
        try {
            while(!isShutdownRequested()){
                doWork();
            }
        } catch (InterruptedException e) {

        }finally {
            doShutdown();
        }
    }

    private void doShutdown() {
        System.out.println("doShutdown: counter = " + counter);
    }

    private void doWork() throws InterruptedException{
        counter++;
        System.out.println("doWork: counter = " + counter);
        TimeUnit.MILLISECONDS.sleep(500);
    }

}
