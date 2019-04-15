package com.xpj.observer.thread;

public interface Observable {

    //任务生命周期的枚举
    enum Cycle{
        STARTED, RUNNING, DONE, ERROR
    }

    //获取当前任务的生命周期状态
    Cycle getCycle();

    //定义启动线程的方法，主要作用是屏蔽Thread的其他方法
    void start();

    //定义线程的打断方法，作用同start
    void interrupt();
}
