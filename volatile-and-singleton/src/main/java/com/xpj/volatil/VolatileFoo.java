package com.xpj.volatil;

import java.util.concurrent.TimeUnit;

/**
 * volatile有两个作用：1、保证共享变量的可见性。2、禁止指令重排。
 */
public class VolatileFoo {

    private static final int MAX = 5;
    /**
     * 分别使用volatile修饰和不使用volatile修饰该变量，运行程序，并观察程序结果
     */
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
            int localValue = initValue;
            while(initValue < MAX) {
                System.out.println("the initValue will be updated to " + ++localValue);
                initValue = localValue;
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Updater").start();

    }
}
