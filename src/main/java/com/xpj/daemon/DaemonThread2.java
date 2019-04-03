package com.xpj.daemon;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @Author: xupeiji
 * @Date: Created in 2019/4/3 14:01
 */
public class DaemonThread2 {

    /** 1、t是守护线程 inner是守护线程 保证inner启动且运行时间比t长，当t结束时，inner线程也结束*/
    /** 2、t是守护线程 由t创建的线程inner默认也是守护线程 在Thread的init方法中默认设置了this.daemon = parent.isDaemon();*/
    /** 3、t是守护线程 由t创建的线程手动设置为非守护线程时，t结束时，inner继续运行*/
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                Thread inner = new Thread(() -> {
                    try {
                        System.out.println("inner thread running");
                        TimeUnit.SECONDS.sleep(10);
                        System.out.println("inner thread done");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                inner.setDaemon(false);
                inner.start();
                System.out.println("inner thread start");


                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("t thread done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.setDaemon(true);
        t.start();

        TimeUnit.SECONDS.sleep(6);
        System.out.println("main thread done");
    }
}
