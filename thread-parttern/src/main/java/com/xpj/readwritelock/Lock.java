package com.xpj.readwritelock;

public interface Lock {
    //获取显示锁，没有获得锁的线程将被阻塞。
    void lock() throws InterruptedException;

    //释放获得的锁
    void unlock();

}
