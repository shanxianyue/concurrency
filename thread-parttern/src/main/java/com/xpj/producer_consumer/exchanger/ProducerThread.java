package com.xpj.producer_consumer.exchanger;

import java.util.Random;
import java.util.concurrent.Exchanger;

public class ProducerThread extends Thread{

    private final Exchanger<char[]> exchanger;
    private char[] buffer = null;
    private char index = 0;
    private final Random random;

    public ProducerThread(Exchanger<char[]> exchanger, char[] buffer, long seed) {
        super("ProducerThread");
        this.exchanger = exchanger;
        this.buffer = buffer;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        try {
            while(true){
                /**填充字符，直至缓冲区buffer被填满*/
                for (int i = 0; i < buffer.length; i++) {
                    buffer[i] = nextChar();
                    System.out.printf("%s: %c ->  \n", Thread.currentThread().getName(), buffer[i]);
                }
                /**
                 * 使用exchange方法将填满的缓冲区传递给ConsumerThread
                 * 传递缓冲区后，作为交换，接收空的缓冲区
                 */
                System.out.printf("%s: BEFORE exchange   \n", Thread.currentThread().getName());
                buffer = exchanger.exchange(buffer);
                System.out.printf("%s: AFTER exchange  \n", Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {

        }
    }

    private char nextChar() throws InterruptedException {
        char c = (char)('A' + index % 26);
        index++;
        Thread.sleep(random.nextInt(1000));
        return c;
    }
}
