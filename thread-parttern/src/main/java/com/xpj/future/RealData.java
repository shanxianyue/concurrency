package com.xpj.future;

import java.util.concurrent.TimeUnit;

public class RealData implements Data {

    private final String content;

    public RealData(int count, char c) {
        System.out.printf("making realdata(%d, %c) BEGIN \n", count, c);
        char[] buffer = new char[count];
        for (int i = 0; i < count; i++) {
            buffer[i] = c;
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        System.out.printf("making realdata(%d, %c) END \n", count, c);
        this.content = new String(buffer);
    }

    @Override
    public String getContent() {
        return this.content;
    }
}
