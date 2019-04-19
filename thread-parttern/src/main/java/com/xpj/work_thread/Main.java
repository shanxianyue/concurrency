package com.xpj.work_thread;

public class Main {
    public static void main(String[] args) {
        Channel channel = new Channel(5);
        channel.startWorkers();
        new ClientThread("A", channel).start();
        new ClientThread("B", channel).start();
        new ClientThread("C", channel).start();
    }
}
