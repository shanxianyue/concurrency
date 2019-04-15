package com.xpj.readwritelock;

import java.util.stream.IntStream;

public class Client {

    private final static String text = "abcdefghijklmn";

    public static void main(String[] args) {
        final ShareData shareData = new ShareData(50);
        IntStream.range(0, 2).forEach(i -> new Thread(() -> {
            IntStream.range(0, text.length()).forEach(index -> {
                try {
                    char c = text.charAt(index);
                    shareData.write(c);
                    System.out.println(Thread.currentThread().getName() + " write " + c);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }, "write" + i).start());

        IntStream.range(0, 10).forEach(i -> new Thread(() -> {

            while(true){
                try {
                    System.out.println(Thread.currentThread().getName() + " read "
                            + new String(shareData.read()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "read" + i).start());
    }
}
