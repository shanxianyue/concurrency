package com.xpj.guarded;

import java.util.concurrent.TimeUnit;

public class Client {

    public static void main(String[] args) throws InterruptedException {
        final RequestQueue queue = new RequestQueue();
        new ClientThread(queue, "a").start();
        ServerThread serverThread = new ServerThread(queue);
        serverThread.start();

        TimeUnit.SECONDS.sleep(1);

        serverThread.close();
    }
}
