package com.xpj.two_phase_termination.example1;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("main BEGIN");
        try {
            CountupThread t = new CountupThread();
            t.start();

            TimeUnit.SECONDS.sleep(10);

            System.out.println("main: shutdownRequest");
            t.shutdownRequest();

            System.out.println("main join");
            t.join();
        } catch (InterruptedException e) {
        }

        System.out.println("main end");
    }
}
