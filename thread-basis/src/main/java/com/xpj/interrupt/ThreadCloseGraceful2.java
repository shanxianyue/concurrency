package com.xpj.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @Author: xupeiji
 * @Date: Created in 2019/4/3 17:16
 */
public class ThreadCloseGraceful2 {

    private static class Worker extends Thread{
        @Override
        public void run() {
            while(true){
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    break;
//                }
                if (Thread.interrupted()){
                    break;
                }
            }
        }
    }

    /**通过捕获InterruptedException或判断中断状态来停掉一个线程*/
    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        worker.start();

        TimeUnit.SECONDS.sleep(10);

        worker.interrupt();
    }
}
