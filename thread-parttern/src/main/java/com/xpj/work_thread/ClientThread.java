package com.xpj.work_thread;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ClientThread extends Thread {
    private final Channel channel;
    private static final Random random = new Random();

    public ClientThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        int i = 0;
        try {
            while (true){
                channel.putRequest(new Request(getName(), i));
                i++;
                TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
        }
    }
}
