package com.xpj.community;

import java.util.stream.Stream;

/**
 * Description:
 *
 * @Author: xupeiji
 * @Date: Created in 2019/4/4 14:50
 */
public class ProduceConsumerVersion2 {

    public static void main(String[] args) {

        Worker2 worker2 = new Worker2();
        Stream.of("P", "P").forEach(n ->
                new Thread(() -> {
                    while (true) {
                        worker2.produce();
                    }
                }, n).start()
        );
        Stream.of("C", "C").forEach(n ->
                new Thread(() -> {
                    while (true) {
                        worker2.consumer();
                    }
                }, n).start()
        );
    }
}

class Worker2 {

    private int i = 0;
    private final Object LOCK = new Object();
    private boolean isProduced = false;

    public void produce() {

        synchronized (LOCK) {
            if (isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                /**
                 * 此处代码必须要放在else或者判断状态不用if而使用while，总之要保证wait之后立即检查状态。
                 * 如果wait之后可以立即执行业务逻辑，一旦多个生产者(或消费者)被唤醒，
                 * 就不能保证同一时间内只有一个生产者(或消费者)生产(消费)数据
                 */
                System.out.println(Thread.currentThread().getName() + "->" + ++i);
                isProduced = true;
                LOCK.notifyAll();//此处使用notify时，可能会出现假死的现象 因为notify方法只是唤醒其中一个线程
            }
        }
    }

    public void consumer() {

        synchronized (LOCK) {
            if (!isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println(Thread.currentThread().getName() + "->" + i);
                isProduced = false;
                LOCK.notifyAll();//此处使用notify时，可能会出现假死的现象 因为notify方法只是唤醒其中一个线程
            }
        }
    }
}
