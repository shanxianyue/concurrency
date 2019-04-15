package com.xpj.readwritelock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ShareData {
    /**定义共享数据*/
    private final List<Character> container = new ArrayList<>();

    private final ReadWriteLock readWriteLock = ReadWriteLock.readWriteLock();

    private final Lock readLock = readWriteLock.readLock();

    private final Lock writeLock = readWriteLock.writeLock();

    private final int length;

    public ShareData(int length){
        this.length = length;
        IntStream.range(0, length).forEach(i -> container.add(i, 'c'));
    }

    public char[] read() throws InterruptedException{
        try {
            readLock.lock();
            char[] newBuffer = new char[length];
            IntStream.range(0, length).forEach(i -> newBuffer[i] = container.get(i));
            slowly();
            return newBuffer;
        }finally {
            readLock.unlock();
        }
    }

    public void write(char c) throws InterruptedException{
        try {
            writeLock.lock();
            IntStream.range(0, length).forEach(i -> container.add(i, c));
            slowly();
        } finally {
            writeLock.unlock();
        }
    }

    private void slowly(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
