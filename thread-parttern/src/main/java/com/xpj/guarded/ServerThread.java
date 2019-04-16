package com.xpj.guarded;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ServerThread extends Thread {
    private final RequestQueue queue;
    private final Random random;

    private volatile boolean flag = true;

    public ServerThread(RequestQueue queue){
        this.queue = queue;
        random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        while(flag){
            Request request = queue.takeRequest();
            if (request == null){
                continue;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void close(){
        this.flag = false;
        this.interrupt();
    }
}
