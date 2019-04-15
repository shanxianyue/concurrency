package com.xpj.readwritelock;

public interface ReadWriteLock {

    /**创建reader锁*/
    Lock readLock();

    //创建write锁
    Lock writeLock();

    //获取当前有多少线程正在执行写操作
    int getWritingWriters();

    //获取当前有多少线程正在等待获取reader锁
    int getWaitingWriters();

    //获取当前有多少线程正在进行读操作
    int getReadingReaders();

    static ReadWriteLock readWriteLock(){
        return new ReadWriteLockImpl();
    }

    static ReadWriteLock readWriteLock(boolean preferWriter){
        return new ReadWriteLockImpl(preferWriter);
    }}
