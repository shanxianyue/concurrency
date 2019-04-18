package com.xpj.producer_consumer.exchanger;

import java.util.Random;
import java.util.concurrent.Exchanger;

public class ConsumerThread extends Thread{
    private final Exchanger<char[]> exchanger;
    private char[] buffer = null;
    private final Random random;

    public ConsumerThread(Exchanger<char[]> exchanger, char[] buffer, long seed) {
        super("ConsumerThread");
        this.exchanger = exchanger;
        this.buffer = buffer;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        try {
            while(true){
                /**
                 * 使用exchange方法将空的缓冲区传递给ProducerThread
                 * 传递空的缓冲区后，作为交换，接收被填满字符的缓冲区
                 */
                System.out.printf("%s BEFORE exchange \n", Thread.currentThread().getName());
                buffer = exchanger.exchange(buffer);
                System.out.printf("%s AFTER exchange \n", Thread.currentThread().getName());
                /**
                 * 打印缓冲区的字符
                 */
                for (int i = 0; i < buffer.length; i++) {
                    System.out.printf("%s: -> %c  \n", Thread.currentThread().getName(), buffer[i]);
                    Thread.sleep(random.nextInt(1000));
                }
            }
        } catch (InterruptedException e) {

        }
    }
}
