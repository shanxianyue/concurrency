package com.xpj.producer_consumer.exchanger;

import java.util.concurrent.Exchanger;

public class Main {
    public static void main(String[] args) {
        Exchanger<char[]> exchanger = new Exchanger<>();
        char[] buffer1 = new char[10];
        char[] buffer2 = new char[10];
        new ProducerThread(exchanger, buffer1, System.currentTimeMillis()).start();
        new ConsumerThread(exchanger, buffer2, System.currentTimeMillis()).start();
    }
}
