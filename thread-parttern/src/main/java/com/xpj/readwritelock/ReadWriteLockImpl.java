package com.xpj.readwritelock;

public class ReadWriteLockImpl implements ReadWriteLock {

    //对象锁
    private final Object MUTEX = new Object();

    //当前有多少个线程正在写入
    private int writingWriters = 0;
    //当前有多少个线程正在等待写入
    private int waitingWriters = 0;
    //当前有多少个线程正在read
    private int readingWriters = 0;

    //read和write的偏好设置
    private boolean preferWriter;

    public ReadWriteLockImpl(){
        this(true);
    }

    public ReadWriteLockImpl(boolean preferWriter){
        this.preferWriter = preferWriter;
    }


    @Override
    public Lock readLock() {
        return new ReadLock(this);
    }

    @Override
    public Lock writeLock() {
        return new WriteLock(this);
    }

    /**使写线程的数量增加*/
    void incrementWritingWriters(){
        this.writingWriters++;
    }

    /**使等待写入的线程数量增加*/
    void incrementWaitingWriters(){
        this.waitingWriters++;
    }

    /**使读线程数量增加*/
    void incrementReadingWriters(){
        this.readingWriters++;
    }

    /**使写线程数量减少*/
    void decrementWritingWriters(){
        this.writingWriters--;
    }

    /**使等待写入的线程数量减少*/
    void decrementWaitingWriters(){
        this.waitingWriters--;
    }

    /**使读线程数量减少*/
    void decrementReadingWriters(){
        this.readingWriters--;
    }

    @Override
    public int getWritingWriters() {
        return this.writingWriters;
    }

    @Override
    public int getWaitingWriters() {
        return this.waitingWriters;
    }

    @Override
    public int getReadingReaders() {
        return this.readingWriters;
    }

    /**获取对象锁*/
    public Object getMutex() {
        return this.MUTEX;
    }

    /**是否偏好写锁*/
    public boolean isPreferWriter() {
        return this.preferWriter;
    }

    /**设置写锁偏好*/
    void changePrefer(boolean preferWriter){
        this.preferWriter = preferWriter;
    }
}
