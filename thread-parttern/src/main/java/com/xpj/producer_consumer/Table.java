package com.xpj.producer_consumer;

import java.util.Arrays;

public class Table {
    private final String[] buffer;
    private int tail; //下次put的位置
    private int head; //下次take的位置
    private int count; //buffer中的蛋糕个数

    public Table(int num) {
        this.buffer = new String[num];
        this.tail = 0;
        this.head = 0;
        this.count = 0;
    }

    /**
     *
     * @param cake
     * @throws InterruptedException 表明该方法可以取消
     */
    public synchronized void put(String cake) throws InterruptedException{
        System.out.printf("%s puts %s \n", Thread.currentThread().getName(), cake);
        while(count > buffer.length){ //使用了Guarded Suspension模式 此为守护条件
            wait();
        }

        buffer[tail] = cake;
        count++;
        tail = (tail + 1) % buffer.length;
        notifyAll();
    }

    /**
     *
     * @return
     * @throws InterruptedException 表明该方法可以取消
     */
    public synchronized String take() throws InterruptedException {
        while(count <= 0){ //使用了Guarded Suspension模式 此为守护条件
            wait();
        }

        String cake = buffer[head];
        buffer[head] = null;
        count--;
        head = (head + 1) % buffer.length;
        notifyAll();
        System.out.printf("%s takes %s \n", Thread.currentThread().getName(), cake);
        return cake;
    }


    public synchronized void clear(){
        if(count <= 0){
            return;
        }

        count = 0;
        head = 0;
        this.tail = 0;
        Arrays.asList(buffer).forEach(cake -> cake = null);
    }
}
