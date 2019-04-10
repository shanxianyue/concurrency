package com.xpj.daemon;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @Author: xupeiji
 * @Date: Created in 2019/4/3 14:01
 */
public class DaemonThread {

    /** 当一个守护线程的父线程结束时，该守护线程也会结束。*/
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + " runing");
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println(Thread.currentThread().getName() + " done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.setDaemon(true);
        t.start();
        TimeUnit.SECONDS.sleep(5);
        System.out.println(Thread.currentThread().getName());
    }
}
