package com.xpj.lock;

import java.util.*;

/**
 * Description: 自定义锁
 *
 * @Author: xupeiji
 * @Date: Created in 2019/4/9 9:58
 */
public class BooleanLock implements CustomsizeLock {

    /**判断当前锁是否已经被占用*/
    private boolean isLockHolded;

    /**被阻塞的线程集合*/
    private Collection<Thread> blockedThreads = new ArrayList<>();

    /**持有锁的线程，只有持有锁的线程才能释放锁*/
    private Thread holdLockThread;


    public BooleanLock(){
        this.isLockHolded = false;
    }

    @Override
    public synchronized void lock() throws InterruptedException {
        while(isLockHolded){
            blockedThreads.add(Thread.currentThread());
            this.wait();
        }
        blockedThreads.remove(Thread.currentThread());
        holdLockThread = Thread.currentThread();
        this.isLockHolded = true;
    }

    @Override
    public synchronized void lock(long mills) throws InterruptedException, TimeOutException {
        System.out.println(Thread.currentThread().getName() + " enter lock!");
        long start = System.currentTimeMillis();
        while(isLockHolded){
            blockedThreads.add(Thread.currentThread());
            this.wait(mills);
            if (System.currentTimeMillis() > start + mills){
                blockedThreads.remove(Thread.currentThread());
                throw new TimeOutException(Thread.currentThread().getName() + " wait " + mills +" timeout");
            }
        }
        blockedThreads.remove(Thread.currentThread());
        holdLockThread = Thread.currentThread();
        this.isLockHolded = true;
    }

    @Override
    public synchronized void unlock() {
        if (holdLockThread == Thread.currentThread()) {
            this.isLockHolded = false;
            System.out.println(Thread.currentThread() + " release the lock monitor!");
            this.notifyAll();
        }
    }

    @Override
    public Collection<Thread> getBlockThread() {
        return Collections.unmodifiableCollection(blockedThreads);
    }

    @Override
    public int getBlockedSize() {
        return blockedThreads.size();
    }
}
