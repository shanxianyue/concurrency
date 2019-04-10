package com.xpj.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @Author: xupeiji
 * @Date: Created in 2019/4/3 17:16
 */
public class ThreadCloseForce {

    public static void main(String[] args) throws InterruptedException {
        ThreadService service = new ThreadService();
        long start = System.currentTimeMillis();
        service.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        service.shutdown(10000);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}


class ThreadService{

    private Thread executeThread;

    private boolean finished = false;

    public void execute(Runnable runnable){
        executeThread = new Thread(() -> {
            Thread runner = new Thread(runnable);
            runner.setDaemon(true);
            runner.start();

            try {
                runner.join();
                finished = true;
            } catch (InterruptedException e) {
            }

        }) ;
        executeThread.start();
    }

    public void shutdown(long mills){
        long currentTime = System.currentTimeMillis();
        while(!finished){
            if ((System.currentTimeMillis() - currentTime) >= mills){
                System.out.println("任务超时");
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
}