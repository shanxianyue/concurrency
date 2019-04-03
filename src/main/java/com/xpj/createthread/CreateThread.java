package com.xpj.createthread;

/**
 * @Author: xupeiji
 * @Description:
 * @Date: Created in 2019/4/3 9:54
 * @Modified:
 */
public class CreateThread {
    public static void main(String[] args) {
        /**既传入runnable接口 又重写thread run 启动线程执行的是重写thread的run。*/
        Thread t2 = new Thread(() -> {
            System.out.println("runnable");
        }){
            @Override
            public void run() {
                System.out.println("extends thread");
            }
        };

        /**线程的父线程就是启动该线程的线程，默认线程与其父线程在同一个组*/
        System.out.println("t2 parent threadGroupName: " + Thread.currentThread().getThreadGroup().getName());
        System.out.println("t2 threadGroupName: " + t2.getThreadGroup().getName());
        t2.start();
    }
}
