package com.xpj.thread_per_message;

import java.util.concurrent.TimeUnit;

public class Helper {
    public void handle(int count, char c){
        System.out.printf(" handle(%d,%c)BEGIN \n", count, c);
        for (int i = 0; i < count; i++) {
            slowly();
            System.out.print(c);
        }
        System.out.println("");
        System.out.printf(" handle(%d,%c)END \n", count, c);
    }

    private void slowly(){
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
        }
    }
}
