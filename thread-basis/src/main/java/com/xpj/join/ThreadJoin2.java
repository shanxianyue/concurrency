package com.xpj.join;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Description:
 *
 * @Author: xupeiji
 * @Date: Created in 2019/4/3 15:44
 */
public class ThreadJoin2 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " running");
                TimeUnit.SECONDS.sleep(10);
                System.out.println(Thread.currentThread().getName() + " done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.join(5000); //当前线程只等待thread线程5秒，如果5秒内thread还未结束，当前线程继续执行
        System.out.println("main continue");
        IntStream.range(0, 100)
                .forEach(i -> System.out.println(Thread.currentThread().getName() + "->" + i));
    }
}
