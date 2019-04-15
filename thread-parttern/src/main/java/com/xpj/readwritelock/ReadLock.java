package com.xpj.readwritelock;

/**读锁*/
public class ReadLock implements Lock {

    private final ReadWriteLockImpl readWriteLock;

    public ReadLock(ReadWriteLockImpl readWriteLock) {
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void lock() throws InterruptedException {

        /**使用Mutex作为锁*/
        synchronized (readWriteLock.getMutex()){
            /**若此时有线程正在进行写操作，或者有写线程等待并且偏向写锁时，就会无法获取读锁，只能被挂起*/
            while(readWriteLock.getWritingWriters() > 0
                    || (readWriteLock.isPreferWriter()
                    && readWriteLock.getWaitingWriters() > 0)){
                readWriteLock.getMutex().wait();
            }
            /**成功获取读锁，并使读锁线程数量加1*/
            readWriteLock.incrementReadingWriters();
        }
    }

    @Override
    public void unlock() {
        synchronized (readWriteLock.getMutex()){
            readWriteLock.decrementReadingWriters();
            readWriteLock.changePrefer(true);
            readWriteLock.getMutex().notifyAll();
        }
    }
}
