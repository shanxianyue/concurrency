package com.xpj.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @Author: xupeiji
 * @Date: Created in 2019/4/3 16:12
 */
public class ThreadInterrupt {
    /**interrupt() 方法并不会真正的打断一个线程，只是将一个线程设置为中断状态，并使join wait sleep等方法抛出InterruptedException*/
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " is interrupted");
                    e.printStackTrace();
                }
                System.out.println(1);
            }
        });
        t.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("before t.interrupt() cat t status: " + t.isInterrupted());
        t.interrupt();
        System.out.println("after t.interrupt() cat t status: " + t.isInterrupted());
    }
}
