package com.xpj.volatil;

import java.util.concurrent.TimeUnit;

public class VolatileFoo {

    private static final int MAX = 5;
    private static int initValue = 0;

    public static void main(String[] args) {
        new Thread(() -> {
            int localValue = initValue;
            while(localValue < MAX) {
                if (localValue != initValue){
                    System.out.println("the initValue is updated to " + initValue);
                    localValue = initValue;
                }
            }
        }, "Reader").start();
        new Thread(() -> {
//            int localValue = initValue;
            while(initValue < MAX) {
                System.out.println("the initValue will be updated to " + ++initValue);
//                initValue = localValue;
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Updater").start();

    }
}
