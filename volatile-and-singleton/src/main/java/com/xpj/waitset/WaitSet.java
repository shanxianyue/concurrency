package com.xpj.waitset;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class WaitSet {

    private static final Object LOCK = new Object();

    /**
     * 1、所有对象都会有一个wait set，用来存放调用了该对象wait方法之后进入block状态的线程
     * 2、线程被notify之后，不一定立即得到执行
     * 3、线程从wait set中被唤醒是随机的(抢占式)
     * 4、线程被唤醒后必须重新获取锁，取得锁之后继续执行wait之后的逻辑
     */
    public static void main(String[] args) throws InterruptedException {
        IntStream.rangeClosed(1, 10).forEach(i ->
            new Thread(() -> {
                synchronized (LOCK){
                    try {
                        System.out.println(Thread.currentThread().getName() + " will be in wait set!");
                        LOCK.wait();
                        System.out.println(Thread.currentThread().getName() + " will be out wait set!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, String.valueOf(i)).start()
        );

        TimeUnit.SECONDS.sleep(1);

        IntStream.rangeClosed(1, 10).forEach(i -> {
                synchronized (LOCK){
                    LOCK.notify();
                }
            }
        );
    }
}
