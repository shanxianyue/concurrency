package com.xpj.guarded;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ClientThread extends Thread {
    private final RequestQueue queue;

    private final Random random;

    private String sendValue;
    
    public ClientThread(RequestQueue queue, String sendValue) {
        this.queue = queue;
        random = new Random(System.currentTimeMillis());
        this.sendValue = sendValue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            queue.putRequest(new Request(sendValue));
            try {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
