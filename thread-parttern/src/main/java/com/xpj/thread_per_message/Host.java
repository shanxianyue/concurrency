package com.xpj.thread_per_message;

public class Host {

    private final Helper helper = new Helper();
    public void request(int count, char c){
        System.out.printf("request(%d,%c) BEGIN \n", count, c);
        new Thread(()-> helper.handle(count, c)).start();
        System.out.printf("request(%d,%c) END \n", count, c);
    }
}
