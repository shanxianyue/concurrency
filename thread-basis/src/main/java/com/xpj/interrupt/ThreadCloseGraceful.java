package com.xpj.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @Author: xupeiji
 * @Date: Created in 2019/4/3 17:16
 */
public class ThreadCloseGraceful {

    private static class Worker extends Thread{

        /**通过一个属性控制线程状态*/
        private volatile boolean start = true;

        @Override
        public void run() {
            while(start){

            }
        }

        public void shutdown(){
            this.start = false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        worker.start();

        TimeUnit.SECONDS.sleep(10);

        worker.shutdown();
    }
}
