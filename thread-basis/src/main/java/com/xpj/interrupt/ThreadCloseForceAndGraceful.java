package com.xpj.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @Author: xupeiji
 * @Date: Created in 2019/4/3 17:16
 */
public class ThreadCloseForceAndGraceful {

    public static void main(String[] args) throws InterruptedException {
        ThreadService2 service = new ThreadService2();
        long start = System.currentTimeMillis();
        service.execute(() -> {
            while (true) {
                if (Thread.interrupted()) {
                    break;
                }
                /**很耗时的操作*/
                for (int i = 0; i < 1000000; i++) {
                    System.out.println(i);
                }
            }
        });

        TimeUnit.SECONDS.sleep(1);
//        service.shutdownGraceful();
        service.shutdown(2000);
        long end = System.currentTimeMillis();

        TimeUnit.SECONDS.sleep(1);

        System.err.println(end - start);
//        int i = 1/0;
    }
}


class ThreadService2 {

    private Thread executeThread;
    private Thread ruuner;

    private boolean finished = false;

    public void execute(Runnable runnable) {
        executeThread = new Thread(() -> {
            Thread runner = new Thread(runnable);
            runner.setDaemon(true);
            runner.start();
            this.ruuner = runner;

            try {
                runner.join();
                finished = true;
            } catch (InterruptedException e) {
            }

        });
        executeThread.start();
    }

    /**暴力的方式停止线程，思路：将runner线程设置为守护线程，并在一个非守护线程中启动，通过中断runner线程的父线程暴力停止runner线程。*/
    public void shutdown(long mills) {
        long currentTime = System.currentTimeMillis();
        while (!finished) {
            if ((System.currentTimeMillis() - currentTime) >= mills) {
                System.err.println("任务超时");
                executeThread.interrupt();
                break;
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("执行线程被打断");
            }
        }
        finished = false;
    }


    /**
     * 优雅的方式停止一个线程，思路：通过获取ruuner线程，中断runner。
     * 该方式的缺点是：在开启一个线程时，不能立即进行关闭，因为有可能runner线程的父线程还未启动，此时无法获取runner线程。
     */
    public void shutdownGraceful() {
        while (!finished) {//循环是为了统计执行时间
            if (!ruuner.isInterrupted()) {
                ruuner.interrupt();
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("执行线程被打断");
            }
        }
        finished = false;
    }
}