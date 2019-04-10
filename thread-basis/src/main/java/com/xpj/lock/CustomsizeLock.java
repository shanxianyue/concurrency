package com.xpj.lock;

import java.util.Collection;

/**
 * Description:
 *
 * @Author: xupeiji
 * @Date: Created in 2019/4/9 9:54
 */
public interface CustomsizeLock {
    class TimeOutException extends Exception{
        public TimeOutException(String message) {
            super(message);
        }
    }

    void lock() throws InterruptedException;

    void lock(long mills) throws InterruptedException, TimeOutException;

    void unlock();

    Collection<Thread> getBlockThread();

    int getBlockedSize();
}
