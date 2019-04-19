package com.xpj.work_thread;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Request {
    private final String name; //委托者
    private final int number; //请求编号
    private static final Random random = new Random();

    public Request(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public void execute(){
        System.out.printf("%s executes %s \n", Thread.currentThread().getName(), this);
        try {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
        }
    }

    @Override
    public String toString() {
        return "[ Request from " + name + " No." + number + " ]";
    }
}
