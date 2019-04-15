package com.xpj.readwritelock;

public class WriteLock implements Lock {

    private final ReadWriteLockImpl readWriteLock;

    public WriteLock(ReadWriteLockImpl readWriteLock) {
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void lock() throws InterruptedException {
        synchronized (readWriteLock.getMutex()){
            try {
                /**首先是等待获取写入锁的数量增加*/
                readWriteLock.incrementWaitingWriters();
                /**如果此时有其他线程正在进行读操作、或写操作，那么当前线程挂起*/
                while(readWriteLock.getWritingWriters() > 0
                    || readWriteLock.getReadingReaders() > 0){
                    readWriteLock.getMutex().wait();
                }
            }finally {
                /**此时说明已经获取了写入锁，使等待获取写入锁的数量减少*/
                readWriteLock.decrementWaitingWriters();
            }
            readWriteLock.incrementWritingWriters();
        }
    }

    @Override
    public void unlock() {
        synchronized (readWriteLock.getMutex()){
            readWriteLock.decrementWritingWriters();
            readWriteLock.changePrefer(false);
            readWriteLock.getMutex().notifyAll();
        }
    }
}
