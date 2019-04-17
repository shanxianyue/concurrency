package com.xpj.producer_consumer;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MakerThread extends Thread{
    private final Table table;
    private final Random random;
    private static int id = 0; //蛋糕的流水号（所有糕点师共用）

    public MakerThread(String name, Table table, long seed) {
        super(name);
        this.table = table;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        try {
            while(true){
                TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
                String cake = "[ Cake No." + nextId() + " by " + getName() + " ]";
                table.put(cake);
            }
        } catch (InterruptedException e) {

        }
    }

    public static synchronized int nextId(){
        return id++;
    }
}
