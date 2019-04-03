package com.xpj.join;

import java.util.stream.IntStream;

/**
 * Description:
 *
 * @Author: xupeiji
 * @Date: Created in 2019/4/3 15:44
 */
public class ThreadJoin {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            IntStream.range(0, 100)
                    .forEach(i -> System.out.println(Thread.currentThread().getName() + "->" + i));
        });
        thread.start();
        thread.join(); //当前线程等待thread线程直到thread线程死亡
        IntStream.range(0, 100)
                .forEach(i -> System.out.println(Thread.currentThread().getName() + "->" + i));
    }
}
