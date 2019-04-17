package com.xpj.producer_consumer;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class EaterThread extends Thread{
    private final Table table;
    private final Random random;

    public EaterThread(String name, Table table, long seed) {
        super(name);
        this.table = table;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        try {
            while(true){
                TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
                String take = table.take();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
