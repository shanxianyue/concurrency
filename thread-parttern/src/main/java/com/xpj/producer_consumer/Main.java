package com.xpj.producer_consumer;

public class Main {
    public static void main(String[] args) {
        Table table = new Table(3);
        new MakerThread("maker1", table, 200).start();
        new MakerThread("maker2", table, 200).start();
        new MakerThread("maker3", table, 200).start();

        new EaterThread("eater1", table, 200).start();
        new EaterThread("eater2", table, 200).start();
        new EaterThread("eater3", table, 200).start();
    }
}
