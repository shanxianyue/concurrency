package com.xpj.balking;

public class Main {
    public static void main(String[] args) {
        Data data = new Data("data.txt", "(empty)");
        new ChangerThread("changer", data).start();
        new SaverThread("saver", data).start();
    }
}
