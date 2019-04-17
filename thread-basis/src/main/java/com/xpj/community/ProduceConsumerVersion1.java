package com.xpj.community;

/**
 * Description: 该方案不适用多个消费者或生产的情况
 *
 * @Author: xupeiji
 * @Date: Created in 2019/4/4 14:50
 */
public class ProduceConsumerVersion1 {

    public static void main(String[] args){

        Worker worker = new Worker();

        new Thread(() -> {
            worker.produce();
        }, "P").start();

        new Thread(() -> {
            worker.consumer();
        }, "C").start();
    }
}

class Worker{

    private int i = 0;
    private final Object LOCK = new Object();
    private boolean isProduced = false;

    public void produce(){
        while(true) {
            synchronized (LOCK) {
                if (isProduced){
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println(Thread.currentThread().getName() + "->" + ++i);
                    isProduced = true;
                    LOCK.notify();
                }
            }
        }
    }

    public void consumer(){
        while(true) {
            synchronized (LOCK) {
                if (isProduced){
                    System.out.println(Thread.currentThread().getName() + "->" + i);
                    isProduced = false;
                    LOCK.notify();
                }else{
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
