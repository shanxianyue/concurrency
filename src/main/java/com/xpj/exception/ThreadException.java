package com.xpj.exception;

/**
 * Description: 演示如何捕获线程运行期间的异常
 *
 * @Author: xupeiji
 * @Date: Created in 2019/4/9 16:01
 */
public class ThreadException {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            int i = 1 / 0;
        });

        t.setUncaughtExceptionHandler((thread, e) -> {
            System.out.println(e);
            System.out.println(thread.getName());
            e.printStackTrace();
        });
        t.start();
    }
}
